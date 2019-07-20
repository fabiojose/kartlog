package com.github.fabiojose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.github.fabiojose.model.Log;
import com.github.fabiojose.model.Ranking;

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
			service.rankingOf(arg);
		});
	}
	
	@Test
	public void output_is_null_err() {
		// setup
		LogService service = new LogService();
		
		// assert
		assertThrows(NullPointerException.class, () -> {
			// act
			service.report(Stream.ofNullable(null), null);
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
			service.rankingOf(arg);
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
			service.rankingOf(arg);
		});
	}
	
	@Test
	public void file_found() throws Exception {
		// setup
		String arg = "./target/test-classes/input.txt";
		LogService service = new LogService();
		
		// act and assert
		service.rankingOf(arg);
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
	
	@Test
	public void ganhador_duas_voltas_ok() throws Exception {
		// setup
		String expectedPilotoCodigo = "033";
		String expectedPilotoNome = "R.BARRICHELLO";
		String arg = "./target/test-classes/input2.txt";
		LogService service = new LogService();
		
		// act
		Stream<Ranking> ranking = service.rankingOf(arg);
		Optional<Ranking> actual = 
				ranking.findFirst();
		
		// assert
		assertTrue(actual.isPresent());
		actual.ifPresent(r -> {
			assertEquals(expectedPilotoCodigo, r.getPiloto().getCodigo());
			assertEquals(expectedPilotoNome, r.getPiloto().getNome());
		});
		
	}
	
	@Test
	public void ganhador__ok() throws Exception {
		// setup
		String expectedPilotoCodigo = "038";
		String expectedPilotoNome = "F.MASSA";
		String arg = "./target/test-classes/input.txt";
		LogService service = new LogService();
		
		// act
		Stream<Ranking> ranking = service.rankingOf(arg);
		
		Optional<Ranking> actual = 
				ranking.findFirst();
		
		// assert
		assertTrue(actual.isPresent());
		actual.ifPresent(r -> {
			assertEquals(expectedPilotoCodigo, r.getPiloto().getCodigo());
			assertEquals(expectedPilotoNome, r.getPiloto().getNome());
		});
		
	}
}
