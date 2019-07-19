package com.github.fabiojose.model;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author fabiojose
 *
 */
public class Piloto {
	
	private static final String HIFFEN = "–";

	private String codigo;
	private String nome;
	
	public Piloto(String codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public String getNome() {
		return nome;
	}
	
	
	@Override
	public String toString() {
		return "Piloto [codigo=" + codigo + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piloto other = (Piloto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	/**
	 * Parses strings with this format: {@code 038 – F.MASSA}
	 * @param string 
	 * @return
	 */
	public static Piloto fromString(String string) {
		requireNonNull(string);
		
		String entry = 
			Stream.of(string)
				.map(String::trim)
				.filter(e -> !e.isEmpty())
				.filter(e -> e.contains(HIFFEN))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException());
		
		List<String> splitted = 
			Stream.of(entry)		
				.map(e -> e.split(HIFFEN))
				.map(Arrays::asList)
				.flatMap(List::stream)
				.map(String::trim)
				.collect(Collectors.toList());
			
		Iterator<String> pilotoFields = splitted.iterator();;
		String codigo = pilotoFields.next();
		String nome   = pilotoFields.next();
		
		return new Piloto(codigo, nome);
	}
}
