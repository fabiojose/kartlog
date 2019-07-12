package com.github.fabiojose.model;

import java.time.Duration;
import java.time.LocalTime;

/**
 * 
 * @author fabiojose
 *
 */
public class Log {

	private LocalTime hora;	
	private Piloto piloto;
	private short volta;
	private Duration tempoVolta;
	private float velocidadeMediaVolta;
	
	public Log(LocalTime hora, Piloto piloto, short volta, Duration tempoVolta,
			float velocidadeMediaVolta) {
		this.hora = hora;
		this.piloto = piloto;
		this.volta = volta;
		this.tempoVolta = tempoVolta;
		this.velocidadeMediaVolta = velocidadeMediaVolta;
	}
	public LocalTime getHora() {
		return hora;
	}
	public Piloto getPiloto() {
		return piloto;
	}
	public short getVolta() {
		return volta;
	}
	public Duration getTempoVolta() {
		return tempoVolta;
	}
	public float getVelocidadeMediaVolta() {
		return velocidadeMediaVolta;
	}
}
