package it.polito.tdp.extflightdelays.model;

public class StatoProbabilita implements Comparable<StatoProbabilita>{
	private String stato;
	private Double probabilita;
	
	public StatoProbabilita(String stato, Double probabilita) {
		super();
		this.stato = stato;
		this.probabilita = probabilita;
	}

	
	public String getStato() {
		return stato;
	}


	public Double getProbabilita() {
		return probabilita;
	}


	@Override
	public int compareTo(StatoProbabilita o) {
		
		return probabilita.compareTo(o.getProbabilita());
	}


	@Override
	public String toString() {
		return  stato + " " + probabilita ;
	}
	
	

}
