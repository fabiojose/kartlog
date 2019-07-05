package com.github.fabiojose.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author fabiojose
 *
 */
public class LogFactoryTests {

	@Test
	public void null_log_entry_err() {
		// setup
		String logEntry = null;
				
		// assert
		assertThrows(NullPointerException.class, () -> {
			// act
			LogFactory.fromLogEntry(logEntry);
		});
	}
	
	@Test
	public void empty_log_entry_err() {
		// setup
		String logEntry = "";
				
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			LogFactory.fromLogEntry(logEntry);
		});
	}
	
	@Test
	public void hora_entry_ok() {
		// setup
		String expected = "23:49:08.277";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		String actual = 
			LogFactory.extractField(logEntry,
				0, 
				11);
			
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void piloto_entry_ok() {
		// setup
		String expected = "038 – F.MASSA";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		String actual = 
			LogFactory.extractField(logEntry,
				18, 
				57);
			
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void volta_entry_ok() {
		// setup
		String expected = "1";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		String actual = 
			LogFactory.extractField(logEntry,
				58, 
				60);
			
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void tempo_volta_entry_ok() {
		// setup
		String expected = "1:02.852";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		String actual = 
			LogFactory.extractField(logEntry,
				61, 
				76);
			
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void velocidade_volta_entry_ok() {
		// setup
		String expected = "44,275";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		String actual = 
			LogFactory.extractField(logEntry,
				80, 
				86);
			
		// assert
		assertEquals(expected, actual);
	}
	
}
