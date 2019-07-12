package com.github.fabiojose.model;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author fabiojose
 *
 */
public class LogFactory {
	
	private static final Logger log = 
			LoggerFactory.getLogger(LogFactory.class);
	
	private static final Locale PT_BR = new Locale("pt", "BR");
	
	private static final int HORA_START_INDEX = 0;
	private static final int HORA_END_INDEX = 11;
	
	private static final int PILOTO_START_INDEX = 18;
	private static final int PILOTO_END_INDEX = 57;
	
	private static final int VOLTA_START_INDEX = 58;
	private static final int VOLTA_END_INDEX = 60;
	
	private static final int TEMPO_VOLTA_START_INDEX = 61;
	private static final int TEMPO_VOLTA_END_INDEX = 76;
	
	private static final int VELOCIDADE_MEDIA_VOLTA_START_INDEX = 80;
	private static final int VELOCIDADE_MEDIA_VOLTA_END_INDEX = 86;
	
	static String extractField(String entry, int startIndex, int endIndex) {
		
		log.debug("Extract: startIndex={}, endIndex={}, >{}<",
				startIndex, endIndex, entry);
		
		return IntStream.range(startIndex, entry.length())
				.filter(i -> i <= endIndex)
				.mapToObj(i -> entry.charAt(i))
				.map(Object::toString)
				.collect(Collectors.joining())
				.trim();
	}
	
	static Duration toDuration(String time) {
		
		LocalTime midnight = LocalTime.parse("00:00:00");
		
		LocalTime durationAsLocalTime =
		LocalTime.parse("00:" + time, 
				DateTimeFormatter.ofPattern("[H:]m:ss[.SSS]"));
		
		return Duration.between(midnight, durationAsLocalTime);
	}
	
	static Float parsePtBRFloat(String value) throws ParseException {
		
		DecimalFormatSymbols brasilFloat = new DecimalFormatSymbols(PT_BR);
		brasilFloat.setDecimalSeparator(',');
		
		DecimalFormat decimalFormat = new DecimalFormat("0.#", brasilFloat);
		
		return decimalFormat.parse(value).floatValue();
	}
	
	public static Log fromLogEntry(String logEntry) {
		requireNonNull(logEntry);
		
		String entry = 
			Stream.of(logEntry)
				.map(String::trim)
				.filter(e -> !e.isEmpty())
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException());
	
		String horaString = extractField(entry,
				HORA_START_INDEX, HORA_END_INDEX);
		log.debug("Hora: >{}<", horaString);
		
		LocalTime hora = LocalTime.parse(horaString);
		log.debug("[PARSED] Hora: {}", hora);
		
		String pilotoString = extractField(entry,
				PILOTO_START_INDEX, PILOTO_END_INDEX);
		log.debug("Piloto: >{}<", pilotoString);
		
		Piloto piloto = Piloto.fromString(pilotoString);
		log.debug("[PARSED] Piloto: {}", piloto);
		
		String voltaString = extractField(entry,
				VOLTA_START_INDEX, VOLTA_END_INDEX);
		log.debug("Volta: >{}<", voltaString);
		
		Short volta = Short.parseShort(voltaString);
		log.debug("[PARSED] Volta: {}", volta);
		
		String tempoVoltaString = extractField(entry,
				TEMPO_VOLTA_START_INDEX, TEMPO_VOLTA_END_INDEX);
		log.debug("Tempo Volta: >{}<", tempoVoltaString);

		Duration tempoVolta = toDuration(tempoVoltaString);
		log.debug("[PARSED] Tempo Volta: {}", tempoVolta);
		
		String velocidadeMediaVoltaString = extractField(entry,
				VELOCIDADE_MEDIA_VOLTA_START_INDEX,
				VELOCIDADE_MEDIA_VOLTA_END_INDEX);
		log.debug("Velocidade Média Volta: >{}<", velocidadeMediaVoltaString);
		
		float velocidadeMediaVolta = 0;
		try {
			velocidadeMediaVolta = 
					parsePtBRFloat(velocidadeMediaVoltaString);
			log.debug("[PARSED] Velocidade Média Volta: {}",
					velocidadeMediaVolta);
		}catch(ParseException e) {
			log.error(e.getMessage(), e);
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		return new Log(hora, piloto, volta, tempoVolta, velocidadeMediaVolta);
	}
}
