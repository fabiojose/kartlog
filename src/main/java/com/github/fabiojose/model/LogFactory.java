package com.github.fabiojose.model;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
	
	private static final int FIRST_MATCH = 1;
	
	private static final String LOG_REGEX = 
			"(\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3})\\s*(\\d{1,3}\\s*[\\–\\-]\\s*[\\w\\.]*)\\s*(\\d{1,3})\\s*(\\d{1,2}:\\d{1,2}\\.\\d{1,3})\\s*(\\d{1,2}\\,\\d{1,4})";
	
	private static final Locale PT_BR = new Locale("pt", "BR");
	
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
	
	static Stream<String> fieldsOf(String entry) {
		
		Pattern pattern = Pattern.compile(LOG_REGEX);
		Matcher matcher = pattern.matcher(entry);
		
		matcher.matches();
		
		return
			IntStream.range(FIRST_MATCH, matcher.groupCount() +1)
				.mapToObj(index -> matcher.group(index))
				.peek(match -> log.debug("Group match: >{}<", match));
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
		
		Iterator<String> fields = fieldsOf(entry).iterator();
	
		
		String horaString = fields.next();
		log.debug("Hora: >{}<", horaString);
		
		LocalTime hora = LocalTime.parse(horaString);
		log.debug("[PARSED] Hora: {}", hora);
		
		String pilotoString = fields.next();
		log.debug("Piloto: >{}<", pilotoString);
		
		Piloto piloto = Piloto.fromString(pilotoString);
		log.debug("[PARSED] Piloto: {}", piloto);
		
		String voltaString = fields.next();
		log.debug("Volta: >{}<", voltaString);
		
		Short volta = Short.parseShort(voltaString);
		log.debug("[PARSED] Volta: {}", volta);
		
		String tempoVoltaString = fields.next();
		log.debug("Tempo Volta: >{}<", tempoVoltaString);

		Duration tempoVolta = toDuration(tempoVoltaString);
		log.debug("[PARSED] Tempo Volta: {}", tempoVolta);
		
		String velocidadeMediaVoltaString = fields.next();
		log.debug("Velocidade Média Volta: >{}<", velocidadeMediaVoltaString);
		
		float velocidadeMediaVolta = 0;
		try {
			velocidadeMediaVolta = 
					parsePtBRFloat(velocidadeMediaVoltaString);
			log.debug("[PARSED] Velocidade Média Volta: {}",
					velocidadeMediaVolta);
		}catch(ParseException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		return new Log(hora, piloto, volta, tempoVolta, velocidadeMediaVolta);
	}
}
