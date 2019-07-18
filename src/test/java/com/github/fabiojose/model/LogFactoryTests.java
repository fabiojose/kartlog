package com.github.fabiojose.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

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
	public void duration_ok() {
		String time = "1:00.000";
		Duration expected = Duration.ofMinutes(1);
		
		// act
		Duration actual = LogFactory.toDuration(time);
		
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void float_ptbr_err() {
		String floatValue = "K3K0";
		
		assertThrows(ParseException.class, () -> {
			LogFactory.parsePtBRFloat(floatValue);
		});
	}
	
	@Test
	public void float_ptbr_ok() throws ParseException {
		float expected = 3.345f;
		String floatValue = "3,345";
		
		float actual = LogFactory.parsePtBRFloat(floatValue);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void hora_entry_ok() {
		// setup
		String expected = "23:49:08.277";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Stream<String> fields = LogFactory.fieldsOf(logEntry);
		String actual = fields
			.findFirst()
			.get();
			
		// assert
		assertEquals(expected, actual);
	} 
	
	@Test
	public void hora_entry_parse_err() {
		// setup
		String logEntry = "32:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// assert
		assertThrows(DateTimeParseException.class, () -> {
			// act
			LogFactory.fromLogEntry(logEntry);
		});
	}
	
	@Test
	public void hora_entry_parse_ok() {
		// setup
		LocalTime expected = LocalTime.parse("23:49:08.277");
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Log actual = LogFactory.fromLogEntry(logEntry);
					
		// assert
		assertEquals(expected, actual.getHora());
	}
	
	@Test
	public void piloto_entry_ok() {
		// setup
		String expected = "038 – F.MASSA";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Stream<String> fields = LogFactory.fieldsOf(logEntry);
		String actual = fields
			.skip(1)
			.findFirst()
			.get();

		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void piloto_entry_parse_ok() {
		// setup
		Piloto expected = new Piloto("038", "F.MASSA");
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Log actual = LogFactory.fromLogEntry(logEntry);
		
		// assert
		assertEquals(expected.getCodigo(), actual.getPiloto().getCodigo());
		assertEquals(expected.getNome(), actual.getPiloto().getNome());
	}
	
	@Test
	public void volta_entry_ok() {
		// setup
		String expected = "1";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Stream<String> fields = LogFactory.fieldsOf(logEntry);
		String actual = fields
			.skip(2)
			.findFirst()
			.get();
			
		// assert
		assertEquals(expected, actual);
	}

	@Test
	public void volta_entry_parse_err() {
		// setup
		String logEntry = "23:49:08.277      038 – F.MASSA                           $		1:02.852           44,275";
		
		// assert
		assertThrows(IllegalStateException.class, () -> {
			// act
			LogFactory.fromLogEntry(logEntry);
		});
		
	}
	
	@Test
	public void volta_entry_parse_ok() {
		// setup
		short expected = Short.valueOf("1");
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Log actual = LogFactory.fromLogEntry(logEntry);
					
		// assert
		assertEquals(expected, actual.getVolta());
		
	}
	
	@Test
	public void tempo_volta_entry_ok() {
		// setup
		String expected = "1:02.852";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Stream<String> fields = LogFactory.fieldsOf(logEntry);
		String actual = fields
			.skip(3)
			.findFirst()
			.get();

		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void tempo_volta_entry_parse_err() {
		// setup
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1K02@852           44,275";
		
		// assert
		assertThrows(IllegalStateException.class, () -> {
			LogFactory.fromLogEntry(logEntry);
		});
	}
	
	@Test
	public void tempo_volta_entry_parse_ok() {
		// setup
		Duration expected = Duration.parse("PT1M2.852S");
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Log actual = LogFactory.fromLogEntry(logEntry);
		
		assertEquals(expected, actual.getTempoVolta());
		
	}
	
	@Test
	public void velocidade_volta_entry_ok() {
		// setup
		String expected = "44,275";
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		// act
		Stream<String> fields = LogFactory.fieldsOf(logEntry);
		String actual = fields
			.skip(4)
			.findFirst()
			.get();
			
		// assert
		assertEquals(expected, actual);
	}
	
	@Test
	public void velocidade_volta_entry_parse_ok() {
		// setup
		float expected = 44.275f;
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           44,275";
		
		Log actual = LogFactory.fromLogEntry(logEntry);
		
		assertEquals(expected, actual.getVelocidadeMediaVolta());
	}
	
	@Test
	public void velocidade_volta_entry_parse_err() {
		String logEntry = "23:49:08.277      038 – F.MASSA                           1		1:02.852           Y4,275";
		
		assertThrows(IllegalStateException.class, () -> {
			LogFactory.fromLogEntry(logEntry);
		});
	}
	
	@Test
	public void log_entry_count_ok() {
		int expected = 5;
		String logEntry = "23:49:08.277      038 – F.MASSA               		         1		1:02.852           4,275";
		
		Stream<String> actual = LogFactory.fieldsOf(logEntry);
		
		assertEquals(expected, actual.count());
	}
}
