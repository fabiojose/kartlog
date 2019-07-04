package com.github.fabiojose.model;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 
 * @author fabiojose
 *
 */
public class LogFactory {
/*
23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275
 */
	
	private static final int HORA_START_INDEX = 0;
	private static final int HORA_END_INDEX = 11;
	
	private static final int PILOTO_START_INDEX = 18;
	private static final int PILOTO_END_INDEX = 57;
	
	private static final int VOLTA_START_INDEX = 58;
	private static final int VOLTA_END_INDEX = 60;
	
	private static final int TEMPO_VOLTA_START_INDEX = 61;
	private static final int TEMPO_VOLTA_END_INDEX = 76;
	
	static String extractField(String entry, int startIndex, int endIndex) {
		
		return IntStream.range(startIndex, entry.length())
				.filter(i -> i <= endIndex)
				.mapToObj(i -> entry.charAt(i))
				.map(Object::toString)
				.collect(Collectors.joining())
				.trim();
	}
	
	public static Log fromLogEntry(String logEntry) {
		requireNonNull(logEntry);
		
		String entry = 
			Stream.of(logEntry)
				.filter(e -> !e.isEmpty())
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException());
	
		String horaString = extractField(entry, HORA_START_INDEX, HORA_END_INDEX);
		System.out.println(horaString);
		
		return null;
	}
}
