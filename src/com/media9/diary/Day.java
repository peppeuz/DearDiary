package com.media9.diary;

public class Day {
	private long id;
	private String data;
	private String testo;
	private String nomefoto;
	
	public Day(String data, String testo) {
		this.data = data;
		this.testo = testo;
	}
public Day(String data, String testo, String nomefoto) {
		this.nomefoto=nomefoto;
		this.data = data;
		this.testo = testo;
	}
	public long getId() {
		return id;
	}
	public String getNomefoto() {
		return nomefoto;
	}
	public void setNomefoto(String nomefoto) {
		this.nomefoto = nomefoto;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setData(String data) {
		this.data = data;
	}
	

	public String getData() {
		return data;
	}
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	

}