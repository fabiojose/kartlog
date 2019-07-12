package com.github.fabiojose.service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * 
 * @author fabiojose
 *
 */
public class LogService {

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
