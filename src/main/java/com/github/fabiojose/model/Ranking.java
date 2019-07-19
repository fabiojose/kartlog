package com.github.fabiojose.model;

import java.time.Duration;
import java.util.List;

/**
 * 
 * @author fabiojose
 *
 */
public class Ranking {

	private short posicao;
	private Piloto piloto;
	private short voltasCompletadas;
	private Duration tempoTotalProva;
	
	private List<Log> logs;
	private Log ultimaVolta;
	
	public Ranking(short posicao, Piloto piloto, short voltasCompletadas,
			Duration tempoTotalProva, List<Log> logs) {
		this.posicao = posicao;
		this.piloto = piloto;
		this.voltasCompletadas = voltasCompletadas;
		this.tempoTotalProva = tempoTotalProva;
		this.logs = logs;
	}
	
	private Ranking(short posicao, Piloto piloto, short voltasCompletadas,
			Duration tempoTotalProva, List<Log> logs, Log ultimaVolta) {
		this.posicao = posicao;
		this.piloto = piloto;
		this.voltasCompletadas = voltasCompletadas;
		this.tempoTotalProva = tempoTotalProva;
		this.logs = logs;
		this.ultimaVolta = ultimaVolta;
	}
	
	public short getPosicao() {
		return posicao;
	}
	public Piloto getPiloto() {
		return piloto;
	}
	public short getVoltasCompletadas() {
		return voltasCompletadas;
	}
	public Duration getTempoTotalProva() {
		return tempoTotalProva;
	}
	public List<Log> getLogs() {
		return logs;
	}
	public Log getUltimaVolta() {
		return ultimaVolta;
	}

	@Override
	public String toString() {
		return "Ranking [posicao=" + posicao + ", piloto=" + piloto 
				+ ", voltasCompletadas=" + voltasCompletadas
				+ ", tempoTotalProva=" + tempoTotalProva + "]";
	}
	
	public static Ranking ofUltimaVolta(Ranking base, Log ultimaVolta) {
		
		return new Ranking(base.getPosicao(), base.getPiloto(),
				ultimaVolta.getVolta(), base.getTempoTotalProva(),
				base.getLogs(), ultimaVolta);
	}
	
	public static Ranking ofPosicao(Ranking base, short posicao) {
		
		return new Ranking(posicao, base.getPiloto(),
				base.getVoltasCompletadas(),
				base.getTempoTotalProva(), base.getLogs(),
				base.getUltimaVolta());
	}
}
