package com.github.fabiojose;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author fabiojose
 *
 */
public class AppTest {

	@Test
	public void arg_file_err() {
		// setup
		String[] args = {"-file"};
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			App.main(args);
		});
	}
}
