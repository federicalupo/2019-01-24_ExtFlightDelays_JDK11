package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Arco;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<String> cmbBoxStati;

    @FXML
    private Button btnVisualizzaVelivoli;

    @FXML
    private TextField txtT;

    @FXML
    private TextField txtG;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	this.model.creaGrafo();
    	
    	this.cmbBoxStati.getItems().clear();
    	this.cmbBoxStati.getItems().addAll(model.getVerticiOrdinati());
    	this.cmbBoxStati.setValue(model.getVerticiOrdinati().get(0));
    	
    	this.txtResult.appendText("Grafo creato\n#vertici: "+model.nVertici()+"\n#archi: "+model.nArchi()+"\n");
    	
    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	String stato = this.cmbBoxStati.getValue();
    	
    	try {
	    	Integer gg = Integer.valueOf(this.txtG.getText());
	    	Integer t = Integer.valueOf(this.txtT.getText());
	    	this.txtResult.appendText("Turisti per ogni stato: \n");
	    	
	    	Map<String, Integer> simulazione =  model.simulazione(stato, gg, t);
	    	for(String s : simulazione.keySet()) {
	    		this.txtResult.appendText(s+" "+simulazione.get(s)+"\n");
    	}

    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserisci valori corretti");
    	}
    }

    @FXML
    void doVisualizzaVelivoli(ActionEvent event) {
    	this.txtResult.clear();
    	String stato = this.cmbBoxStati.getValue();
    	this.txtResult.appendText("Stati collegati a "+stato+"\n");
    	for(Arco a : model.visualizzaVelivoli(stato)) {
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxStati != null : "fx:id=\"cmbBoxStati\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnVisualizzaVelivoli != null : "fx:id=\"btnVisualizzaVelivoli\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtT != null : "fx:id=\"txtT\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert txtG != null : "fx:id=\"txtG\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
