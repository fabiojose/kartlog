package com.github.fabiojose.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author fabiojose
 *
 */
public class LogServiceTests {
	
	@Test
	public void file_is_null_err() {
		// setup
		String arg = null;
		LogService service = new LogService();
		
		// assert
		assertThrows(NullPointerException.class, () -> {
			// act
			service.load(arg);
		});
	}

	@Test
	public void file_is_not_file_err() {
		// setup
		String arg = "./target";
		LogService service = new LogService();
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			service.load(arg);
		});
	}
	
	@Test
	public void file_not_found() {
		// setup
		String arg = "/tmp/file_rrr_not_found.txt";
		LogService service = new LogService();
		
		// assert
		assertThrows(FileNotFoundException.class, () -> {
			// act
			service.load(arg);
		});
	}
	
}
