package com.github.fabiojose;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.fabiojose.model.Log;
import com.github.fabiojose.service.LogService;

/**
 * Application entry-point
 * 
 * @author fabiojose
 *
 */
public class App {
	
	private static final String ARG_SEPARATOR = "=";
	private static final String FILE_PATH_ARG = "--file=";
	
    public static void main( String[] args ) throws Exception {

    	String filePathArg = 
    	  Arrays.asList(args).stream()
    		.filter(arg -> arg.startsWith(FILE_PATH_ARG))
    		.filter(arg -> !arg.equals(FILE_PATH_ARG))
    		.findFirst()
    		.orElseThrow(() ->
    			new IllegalArgumentException(
    				String.format("Argument '%s' not found", FILE_PATH_ARG)));
    	
    	Optional<String> filePathOptional = 
	    	Optional.ofNullable(
		    	Arrays.asList(filePathArg.split(ARG_SEPARATOR)).stream()
		    		.reduce((name, value) -> value)
		    		.orElse(null)
	    	);
    	
    	String filePathName =
    	filePathOptional.orElseThrow(() -> 
    		new IllegalArgumentException(
    			String.format("Argument '%s' has no value", FILE_PATH_ARG)));
    	
    	final LogService logService = new LogService();
    	
    	Path filePath = logService.pathOf(filePathName);
    	List<Log> logs = logService.valuesOf(filePath);
    	Optional<Log> melhorVolta = logService.melhorVoltaOf(logs);
    	
    	logService.report(logService.rankingOf(logs), melhorVolta.get(),
    			System.out);
    	
    }
}
