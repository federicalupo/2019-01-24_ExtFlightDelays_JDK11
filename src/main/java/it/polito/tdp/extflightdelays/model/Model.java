package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;


import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	private ExtFlightDelaysDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	public Model() {
		this.dao = new ExtFlightDelaysDAO();
	}
	
	public void creaGrafo() {
		this.grafo = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		Graphs.addAllVertices(this.grafo, dao.loadAllStates());
		
		for(String s1 : grafo.vertexSet()) {
			for(String s2: grafo.vertexSet()) {
				if(dao.getVelivoliDistinti(s1,s2) > 0) {
					Graphs.addEdge(this.grafo, s1, s2, dao.getVelivoliDistinti(s1,s2));
				}
			}
		}
	}

	public List<String> getVerticiOrdinati(){
		List<String> vertici = new ArrayList<>(this.grafo.vertexSet());
		return vertici;
	}

	public Integer nVertici() {
		return this.grafo.vertexSet().size();
	}
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Arco> visualizzaVelivoli(String stato){
		List<Arco> archiUscenti = new ArrayList<>();
		
		for(DefaultWeightedEdge d : grafo.outgoingEdgesOf(stato)) {
			archiUscenti.add(new Arco(grafo.getEdgeSource(d), grafo.getEdgeTarget(d), (int)grafo.getEdgeWeight(d)));	
		}
		
		Collections.sort(archiUscenti);
		return archiUscenti;
	}
	
	public Map<String, Integer> simulazione(String stato, Integer gg, Integer t){
		
		Simulazione s = new Simulazione();
		s.init(stato, t, gg, grafo, this);
		s.run();
		
		return s.getStatoTurista();
		
	}
}
