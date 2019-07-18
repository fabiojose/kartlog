package com.github.fabiojose.service;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.github.fabiojose.model.Log;
import com.github.fabiojose.model.LogFactory;

/**
 * 
 * @author fabiojose
 *
 */
public class LogService {
	
	private static final int FIRST_LINE = 1;
	
	Stream<Log> valuesOf(Path filePath) throws IOException {
		
		return 
			Files.lines(filePath)
				.skip(FIRST_LINE)
				.map(line -> LogFactory.fromLogEntry(line));
	
	}

	public void load(String filePathName) throws FileNotFoundException {
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
		
		
    	
	}
	
}
