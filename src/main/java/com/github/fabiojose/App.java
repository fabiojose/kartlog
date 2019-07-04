package com.github.fabiojose;

import java.util.Arrays;

/**
 * Application entry-point
 * 
 * @author fabiojose
 *
 */
public class App {
	
	private static final String FILE_PATH_ARG = "--file=";
	
    public static void main( String[] args ) throws Exception {

    	Arrays.asList(args).stream()
    		.filter(arg -> arg.startsWith(FILE_PATH_ARG))
    		.findFirst()
    		.orElseThrow(() ->
    			new IllegalArgumentException(
    				String.format("Argument %s not found", FILE_PATH_ARG)));
    }
}
