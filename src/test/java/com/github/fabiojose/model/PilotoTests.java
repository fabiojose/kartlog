package com.github.fabiojose.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author fabiojose
 *
 */
public class PilotoTests {

	@Test
	public void string_null_err() {
		// setup
		String piloto = null;
		
		// assert
		assertThrows(NullPointerException.class, () -> {
			// act
			Piloto.fromString(piloto);
		});
	}
	
	@Test
	public void string_empty_err() {
		// setup
		String piloto = "";
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			Piloto.fromString(piloto);
		});
	}
	
	@Test
	public void string_spaces_err() {
		// setup
		String piloto = "  	";
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			Piloto.fromString(piloto);
		});
	}
	
	@Test
	public void string_no_separator_err() {
		// setup
		String piloto = "038F.MASSA                           ";
		
		// assert
		assertThrows(IllegalArgumentException.class, () -> {
			// act
			Piloto.fromString(piloto);
		});
	}
	
	@Test
	public void string_ok() {
		// setup
		Piloto expected = new Piloto("038", "F.MASSA");
		String piloto = "038 – F.MASSA                           ";
		
		// act
		Piloto actual = Piloto.fromString(piloto);
		
		// assert
		assertEquals(expected.getCodigo(), actual.getCodigo());
		assertEquals(expected.getNome(), actual.getNome());
	}
	
	@Test
	public void string_nospaces_ok() {
		// setup
		Piloto expected = new Piloto("038", "F.MASSA");
		String piloto = "038–F.MASSA";
		
		// act
		Piloto actual = Piloto.fromString(piloto);
		
		// assert
		assertEquals(expected.getCodigo(), actual.getCodigo());
		assertEquals(expected.getNome(), actual.getNome());
	}
}
