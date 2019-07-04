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
	
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public Piloto getPiloto() {
		return piloto;
	}
	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}
	public short getVolta() {
		return volta;
	}
	public void setVolta(short volta) {
		this.volta = volta;
	}
	public Duration getTempoVolta() {
		return tempoVolta;
	}
	public void setTempoVolta(Duration tempoVolta) {
		this.tempoVolta = tempoVolta;
	}
	public float getVelocidadeMediaVolta() {
		return velocidadeMediaVolta;
	}
	public void setVelocidadeMediaVolta(float velocidadeMediaVolta) {
		this.velocidadeMediaVolta = velocidadeMediaVolta;
	}
}
