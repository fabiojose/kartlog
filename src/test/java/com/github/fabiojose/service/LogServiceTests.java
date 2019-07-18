package com.github.fabiojose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.github.fabiojose.model.Log;

/**
 * 
 * @author fabiojose
 *
 */
public class LogServiceTests {
	
	private static final int FIRST_VALUE = 1;
	private static final int TO_LAST = 22;
	
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
	
	@Test
	public void file_found() throws Exception {
		// setup
		String arg = "./target/test-classes/input.txt";
		LogService service = new LogService();
		
		// act and assert
		service.load(arg);
	}
	
	@Test
	public void file_log_value1_piloto_ok() throws Exception {
		// setup
		String expectedPilotoCodigo = "038";
		String expectedPilotoNome = "F.MASSA";
		Path arg = Paths.get("./target/test-classes/input1.txt");
		LogService service = new LogService();
		
		// act
		Optional<Log> actual = 
			service.valuesOf(arg)
				.findFirst();
		
		// assert
		assertTrue(actual.isPresent());
		actual.ifPresent(log -> {
			assertEquals(expectedPilotoNome, log.getPiloto().getNome());
			assertEquals(expectedPilotoCodigo, log.getPiloto().getCodigo());
		});
	}
	
	@Test
	public void file_log_value2_piloto_ok() throws Exception {
		// setup
		String expectedPilotoCodigo = "033";
		String expectedPilotoNome = "R.BARRICHELLO";
		Path arg = Paths.get("./target/test-classes/input2.txt");
		LogService service = new LogService();
		
		// act
		Optional<Log> actual = 
			service.valuesOf(arg)
				.skip(FIRST_VALUE)
				.findFirst();
		
		// assert
		assertTrue(actual.isPresent());
		actual.ifPresent(log -> {
			assertEquals(expectedPilotoNome, log.getPiloto().getNome());
			assertEquals(expectedPilotoCodigo, log.getPiloto().getCodigo());
		});
	}
	
	@Test
	public void file_log_value_last_piloto_ok() throws Exception {
		// setup
		Short expectedVolta = 3;
		String expectedPilotoNome = "S.VETTEL";
		Path arg = Paths.get("./target/test-classes/input.txt");
		LogService service = new LogService();
		
		// act
		Optional<Log> actual = 
			service.valuesOf(arg)
				.skip(TO_LAST)
				.findFirst();
		
		// assert
		assertTrue(actual.isPresent());
		actual.ifPresent(log -> {
			assertEquals(expectedPilotoNome, log.getPiloto().getNome());
			assertEquals(expectedVolta.shortValue(), log.getVolta());
		});
	}
	
}
