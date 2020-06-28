package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.extflightdelays.model.Evento.Tipo;

public class Simulazione {
	
	//mondo
	private Map<String, Integer> statoTurista;
	private Graph<String, DefaultWeightedEdge> grafo;
	private Model model;
	
	//input
	private Integer nTuristi;
	private Integer giorni;
	
	//coda
	private PriorityQueue<Evento> coda;
	//output
	
	public void init(String stato, Integer nTuristi, Integer giorni, Graph<String, DefaultWeightedEdge> grafo, Model model) {
		statoTurista = new LinkedHashMap<>(); //ordine di inserimento
		this.nTuristi = nTuristi;
		this.giorni = giorni;
		this.grafo = grafo;
		this.model = model;
		this.coda = new PriorityQueue<>();
		
		
		for(String s:grafo.vertexSet()) { 
			if(!s.equals(stato))
				statoTurista.put(s, 0);
			else
				statoTurista.put(s, nTuristi);
		}
		
		
		for(int i = 0; i<nTuristi; i++ ) {
			String destinazione = this.destinazione(stato);
			
			if(destinazione!=null) {
			
				Evento e = new Evento(Tipo.PARTENZA,1, i, stato, destinazione);
				coda.add(e);
			}
			
		}
		
	}

	public void run() {
		
		while(!coda.isEmpty()) {
			processEvent(coda.poll());
		}
		
		
	}
	
	private void processEvent(Evento e) {
		switch(e.getTipo()) {
		case PARTENZA:
			this.statoTurista.put(e.getPartenza(), this.statoTurista.get(e.getPartenza())-1);
			this.statoTurista.put(e.getDestinazione(), this.statoTurista.get(e.getDestinazione())+1);
			
			if(e.getGiorno()<this.giorni) {
				//altro viaggio
				
				String destinazione = this.destinazione(e.getDestinazione());
					if(destinazione!=null) {
					Evento ev = new Evento(Tipo.PARTENZA,e.getGiorno()+1, e.getTurista(), e.getDestinazione(), destinazione);
					coda.add(ev);	
				}
			}
		
			break;
		}
		
	}
	
	private List<StatoProbabilita> calcolaProb(String stato) {
		Double totale = 0.0;
		List<StatoProbabilita> listaSP = new ArrayList<>();
		
		for(DefaultWeightedEdge d : grafo.outgoingEdgesOf(stato)) {
			totale+=this.grafo.getEdgeWeight(d);
		}
		
		for(Arco a : model.visualizzaVelivoli(stato)) {
		
			Double prob = a.getPeso()/totale;
			
			StatoProbabilita sp;
			
			if(listaSP.size()==0)
				 sp= new StatoProbabilita(a.getStato2(), prob);
			else {
				//FACCIO SOMMA PROBABILITA!!!!
				prob = prob + listaSP.get(listaSP.size()-1).getProbabilita();
				 sp= new StatoProbabilita(a.getStato2(), prob);
			}
			
			listaSP.add(sp);
		}
		
		Collections.sort(listaSP);
		
		return listaSP;

	}
	
	private String destinazione(String stato) {
		List<StatoProbabilita> sP = this.calcolaProb(stato);
		boolean trovato = false;
		String destinazione=null;
		
		Double random = Math.random();
		
		for(StatoProbabilita sp : sP) {
			if(random.compareTo(sp.getProbabilita())<0 && !trovato) {
				trovato = true;
				destinazione = sp.getStato();
			}
		}
		
		return destinazione;
	}

	public Map<String, Integer> getStatoTurista() {
		return statoTurista;
	}
	
	
}
