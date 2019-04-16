package it.polito.tdp.meteo;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.*;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.util.StringConverter;

import java.time.*;
import java.time.format.TextStyle;


public class MeteoController {
	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ChoiceBox<Month> boxMese;

	@FXML
	private Button btnCalcola;

	@FXML
	private Button btnUmidita;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCalcolaSequenza(ActionEvent event) {
		Month m=null;
		txtResult.clear();
		try {
			 m= boxMese.getValue();
			// txtResult.appendText("il mese scelto è "+m);
			 List<Citta> citt= new ArrayList<Citta>();
			 citt=model.trovaSequenza(m);
			txtResult.appendText("La sequenza ottima per ottenere un punteggio minore e': \n"+citt+"\n");
		} catch (NumberFormatException e) {
			txtResult.appendText("Errore");
			return;

		}
		
		
		
		
		

	}

	@FXML
	void doCalcolaUmidita(ActionEvent event) {
		txtResult.clear();
		Month m=null;
		try {
		 m= boxMese.getValue();
		
		}catch (NumberFormatException e) {
		
		txtResult.appendText("Errore ");
		return;
		}
		if(m!=null) {
			
		txtResult.appendText("Risultati: \n"+model.getUmiditaMedia(m));
		 }
		
	}

	@FXML
	void initialize() {
		assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Meteo.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Meteo.fxml'.";
		// popola la boxMese con i 12 mesi dell'anno
				for(int mese = 1; mese <= 12 ; mese ++) {
					boxMese.getItems().add(Month.of(mese)) ;
				}
				// il setConverter serve a definire un metodo alternativo al toString nativo di <Month> per definire
				// l'etichetta del bottone. In questo caso lo covertiamo utilizzando la lingua italiana.
				// La ChoiceBox utilizzerà quindi il toString di questo StringConverter anziché quello di default.
			boxMese.setConverter(new StringConverter<Month>() {
					@Override
					public String toString(Month m) {
						return m.getDisplayName(TextStyle.FULL, Locale.ITALIAN) ;
					}
					
					@Override
					public Month fromString(String string) {
						return null;
					}
				

		});
		
	}

	public void setModel(Model m) {
		this.model = m;
		
	}

}
