package com.github.fabiojose;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

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
	
	@Test
	public void arg_file_empty_err() {
		// setup
		String[] args = {"--file="};
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			App.main(args);
		});
	}
	
	@Test
	public void arg_file_ok() throws Exception {
		// setup
		String[] args = {"--file=./target/test-classes/input.txt"};
		
		// act and assert
		App.main(args);
	}
	
	@Test
	public void arg_file_is_not_file_err() {
		// setup
		String[] args = {"--file=./target"};
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			App.main(args);
		});
	}
	
	@Test
	public void arg_file_not_found() {
		// setup
		String[] args = {"--file=/tmp/file_rrr_not_found.txt"};
		
		// assert
		assertThrows(FileNotFoundException.class, () -> {
			// act
			App.main(args);
		});
	}
}
