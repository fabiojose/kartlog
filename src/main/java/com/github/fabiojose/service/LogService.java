package com.github.fabiojose.service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    	if(Files.isDirectory(filePath)) {
    		throw new IllegalArgumentException(
    			String.format("Path is not a file: %s", filePathName));
    	}
    	
    	if(!filePath.toFile().exists()) {
    		throw new FileNotFoundException(filePathName);
    	}
    	
	}
	
}
