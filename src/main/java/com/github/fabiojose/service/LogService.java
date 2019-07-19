package com.github.fabiojose.service;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.fabiojose.model.Log;
import com.github.fabiojose.model.LogFactory;
import com.github.fabiojose.model.Piloto;
import com.github.fabiojose.model.Ranking;

/**
 * 
 * @author fabiojose
 *
 */
public class LogService {
	
	private static final Logger log = 
			LoggerFactory.getLogger(LogService.class);
	
	private static final int FIRST_LINE = 1;
	
	Stream<Log> valuesOf(Path filePath) throws IOException {
		
		return 
			Files.lines(filePath)
				.skip(FIRST_LINE)
				.map(LogFactory::fromLogEntry);
	
	}
	
	Map<Short, List<Log>> logsByVolta(Stream<Log> logs) {
		
		Map<Short, List<Log>> result =
			logs.collect(Collectors.groupingBy(Log::getVolta));
		
		// sorting
		result.values().stream()
			.forEach(value -> {
				value.sort((log1, log2) -> {
					return log1.getTempoVolta().compareTo(log2.getTempoVolta());
				});
			});
		
		return result;
	}
	
	public Stream<Ranking> rankingOf(String filePathName) throws IOException {
		requireNonNull(filePathName);
		
		Path filePath = Paths.get(filePathName);
		
		filePath = 
			Stream.of(filePath)
				.filter(fp -> !Files.isDirectory(fp))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
	    			String.format("Path is not a file: %s", filePathName)));

		filePath = 
			Stream.of(filePath)
				.filter(fp -> fp.toFile().exists())
				.findFirst()
				.orElseThrow(() -> new FileNotFoundException(filePathName));
				
    	Stream<Log> values = valuesOf(filePath);
    	
    	Map<Piloto, List<Log>> logsByPiloto = values
				.collect(Collectors.groupingBy(Log::getPiloto));
		
		List<Ranking> rank = logsByPiloto.entrySet()
			.stream()
			.map(entry -> {
				// tempo total de prova
				Optional<Duration> tempoTotal = 
					entry.getValue()
						.stream()
						.map(Log::getTempoVolta)
						.map(Duration::toMillis)
						.reduce(Long::sum)
						.map(total -> Duration.ofMillis(total));
				
				return 
					new Ranking((short)0, entry.getKey(), (short)0,
							tempoTotal.get(), entry.getValue());
			})
			.collect(Collectors.toList());

		final AtomicInteger posicao = new AtomicInteger(0);
		return rank.stream()
			// voltas completadas
			.map(ranking -> {
				Optional<Log> ultimaVolta = ranking.getLogs()
					.stream()
					.max((log1, log2) 
							-> log1.getVolta() - log2.getVolta());
				
				return Ranking.ofUltimaVolta(ranking, ultimaVolta.get());
			})
			// ordem de chegada
			.sorted((ranking1, ranking2) -> {
				Log log1 = ranking1.getUltimaVolta();
				Log log2 = ranking2.getUltimaVolta();
				
				Duration tempo1 = log1.getTempoVolta();
				Duration tempo2 = log2.getTempoVolta();
				
				short volta1 = log1.getVolta();
				short volta2 = log2.getVolta();
				
				// ordear por tempo e volta
				return 
					tempo1.compareTo(tempo2) - (volta1 - volta2);
			})
			// posição de chegada
			.map(ranking -> {
				return Ranking.ofPosicao(ranking, 
						(short)posicao.incrementAndGet());
			});

	}
	
	public void report(Stream<Ranking> ranking,
			final OutputStream output) throws IOException {
		requireNonNull(ranking);
		requireNonNull(output);
		
		// Cabeçalho
		output.write(String.format(
			"|%20s|%-20s|%-20s|%20s|%20s|\n",
			"Posição Chegada",
			"Código Piloto",
			"Nome Piloto",
			"Voltas Completadas",
			"Tempo de Prova"
		).getBytes());
		
		ranking.forEach(r -> {
			try { 
				output.write(String.format(
					"|%20d|%-20s|%-20s|%20d|%20s|\n",
					r.getPosicao(),
					r.getPiloto().getCodigo(),
					r.getPiloto().getNome(),
					r.getVoltasCompletadas(),
					
					String.format("%d:%02d:%02d.%03d",
						r.getTempoTotalProva().toHoursPart(),
						r.getTempoTotalProva().toMinutesPart(),
						r.getTempoTotalProva().toSecondsPart(),
						r.getTempoTotalProva().toMillisPart()
					)
				).getBytes());
			}catch(IOException e) {
				log.error(e.getMessage(), e);
			}
		});
	}
}
