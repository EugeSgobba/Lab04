package it.polito.tdp.lab04.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SegreteriaStudentiController {

    ObservableList<String> elencoCorsi;
	
	@FXML
    private ComboBox<String> comboBox;
    @FXML
    private Button cercaIscrittiAlCorso;
    @FXML
    private TextField matricola;
    @FXML
    private Button traduciMatricola;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private Button cercaCorsi;
    @FXML
    private Button iscrivi;
    @FXML
    private TextArea textArea;
    @FXML
    private Button reset;
   
    @FXML
    void initialize() {
    	List<String> elenco=new ArrayList<>();
    	CorsoDAO interrogazioneDB = new CorsoDAO();
    	elenco.add(" ");
    	for(Corso c:interrogazioneDB.getTuttiICorsi()) {
    		elenco.add(c.getNome());
    	}
    	elencoCorsi=FXCollections.observableArrayList(elenco);
    	comboBox.setValue(" ");
    	comboBox.setItems(elencoCorsi);    	
    }
    
    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	StudenteDAO dao1=new StudenteDAO();
    	List<Studente> studenti=new ArrayList<>();
    	studenti=dao1.getTuttiStudenti();
    	int flag=0;
    	for(Studente s:studenti) {
    		if(s.getMatricola()==Integer.valueOf(matricola.getText())) {
    			flag=1;
    			break;
    		}
    	}
    	if(flag==0) {
    		System.out.println("Studente inesistente");
    	}
    	StudenteDAO dao=new StudenteDAO();
    	CorsoDAO dao2=new CorsoDAO();
    	List<String> corsi= new LinkedList<>();
    	List<Corso> corsiTutti=new LinkedList<>();
    	corsi=dao.getCorsiACuiEIscrittoLoStudente(Integer.valueOf(matricola.getText()));
    	corsiTutti=dao2.getTuttiICorsi();
    	int i=0;
    	while(i<corsiTutti.size()) {
    		if(!corsi.contains(corsiTutti.get(i).getCodins()))
    			corsiTutti.remove(i);
    		else
    			i++;
    	}
    	String temp="";
    	for(Corso c:corsiTutti) {
    		temp+=c.toString();
    	}
    	textArea.setText(temp);
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	if(comboBox.getValue().compareTo(" ")==0) {
    		System.out.println("Errore! Selezionare un corso");
    		return;
    	}
    	String codinsDaCercare=null;
    	CorsoDAO dao2 =new CorsoDAO();
    	List<Corso> corsi=new LinkedList<>();
    	corsi=dao2.getTuttiICorsi();
    	for(Corso c: corsi) {
    		if(c.getNome().compareTo(comboBox.getValue())==0) {
    			codinsDaCercare=c.getCodins();
    			break;
    		}
    	}
    	StudenteDAO dao =new StudenteDAO();
    	List<Studente> studenti=new ArrayList<>();
    	studenti=dao.getStudentiIscrittiAlCorso(codinsDaCercare);
    	String temp="";
    	for(Studente s: studenti) {
    		temp+=s.toString()+"\n";    		
    	}
    	textArea.setText(temp);    	
    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    @FXML
    void doReset(ActionEvent event) {

    }

    @FXML
    void doTraduciMatricola(ActionEvent event) {
    	
    	StudenteDAO dao=new StudenteDAO();
    	List<Studente> studenti=new ArrayList<>();
    	studenti=dao.getTuttiStudenti();
    	for(Studente s:studenti) {
    		if(s.getMatricola()==Integer.valueOf(matricola.getText())) {
    			nome.setText(s.getNome());
    			cognome.setText(s.getCognome());
    			return;
    		}
    	}
    	System.out.println("Studente inesistente");
    }

}
