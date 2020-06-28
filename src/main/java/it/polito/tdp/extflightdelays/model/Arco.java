package it.polito.tdp.extflightdelays.model;

public class Arco implements Comparable<Arco>{
	String stato1;
	String stato2;
	Integer peso;
	public Arco(String stato1, String stato2, Integer peso) {
		super();
		this.stato1 = stato1;
		this.stato2 = stato2;
		this.peso = peso;
	}
	public String getStato1() {
		return stato1;
	}
	public String getStato2() {
		return stato2;
	}
	public Integer getPeso() {
		return peso;
	}
	@Override
	public String toString() {
		return stato2 + " " + peso;
	}
	@Override
	public int compareTo(Arco o) {
		return -this.peso.compareTo(o.getPeso());
	}
	
	

}
