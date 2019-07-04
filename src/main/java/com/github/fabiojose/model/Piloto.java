package com.github.fabiojose.model;

/**
 * 
 * @author fabiojose
 *
 */
public class Piloto {

	private String codigo;
	private String nome;
	
	public Piloto() {
		
	}
	
	public Piloto(String codigo, String nome) {
		setCodigo(codigo);
		setNome(nome);
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
