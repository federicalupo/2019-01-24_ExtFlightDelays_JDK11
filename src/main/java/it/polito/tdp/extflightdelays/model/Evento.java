package it.polito.tdp.extflightdelays.model;

public class Evento implements Comparable<Evento>{
	
	public enum Tipo{
		PARTENZA;
	}
	
	private Tipo tipo;
	private Integer giorno;
	private Integer turista;
	private String partenza;
	private String destinazione;
	
	public Evento(Tipo tipo, Integer giorno, Integer turista, String partenza, String destinazione) {
		super();
		this.tipo = tipo;
		this.giorno = giorno;
		this.turista = turista;
		this.partenza = partenza;
		this.destinazione = destinazione;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Integer getGiorno() {
		return giorno;
	}

	public Integer getTurista() {
		return turista;
	}

	public String getPartenza() {
		return partenza;
	}

	public String getDestinazione() {
		return destinazione;
	}

	@Override
	public int compareTo(Evento o) {
	
		return this.giorno.compareTo(o.getGiorno());
	}
	
	

}
