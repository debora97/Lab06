package it.polito.tdp.meteo;

import java.util.*;


import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {
	MeteoDAO dao= new MeteoDAO();

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private List<Citta> citta;
	private List<Citta> best;
	private int punteggio_best;

	public Model() {

	}
	
	// metodo che ti rimanda una lista di tutti i rilevamenti  di tutti i primi giorni del mese  
	// metodo che mi rimanda una lista di rilevamenti con m t g di quel determinato giorno

	public String getUmiditaMedia(int mese) {
		//punto1) 

		return "TODO!";
	}

	public List<Citta> trovaSequenza(int mese) {
		//punto2)
		best= new ArrayList<Citta>();
		punteggio_best=10000;
		List<Citta> parziale= new ArrayList<Citta>();
		cerca(parziale,0,mese);

		return best;
	}

	private void cerca(List<Citta> parziale, int L, int mese) {
		int punteggio=punteggioSoluzione(parziale);
		// casi terminali 
		//reggiunte 15 citta
		if(parziale.size()==NUMERO_GIORNI_TOTALI) {
			//controllo se la soluzione mi conviene 
			if(punteggio<=punteggio_best) {
				best=new ArrayList<Citta>(parziale);
				punteggio_best=punteggio;
				return;
			}else return; //se non mi conviene la scarto 
		}
		if(punteggio>punteggio_best) {
			return;
		}
		
		
		// condizioni

		// una città e' presente più di 6 volte

		// il punteggio è maggiore del punteggio_best
		//minimo 3 giorni di fila in una citta
		
		
		if(controllaParziale(parziale)==true) {
			cerca(parziale, L+1, mese);
		//provo ad aggiungere 
		parziale.add(citta.get(L));
		cerca(parziale, L+1, mese);
		parziale.remove(citta.get(L));//backtracking
		}
		
		
	}

	private int punteggioSoluzione(List<Citta> soluzioneCandidata) {
// metodo che mi calcola il punteggio 
		int score = 0;
		return score;
	}

	private boolean controllaParziale(List<Citta> parziale) {
		//controllo se c'è 6 volte
		if(contaCitta(parziale, "Torino")<=6 && contaCitta(parziale, "Milano")<=6 && contaCitta(parziale, "Genova")<=6) {
			// controllo se c'è 3 volte di fila
			if(controlloMinimo(parziale)==true) {
				return true;
			}
			
		}
		return false;
		
		
	}

	private boolean controlloMinimo(List<Citta> parziale) {
		for(int i=0; i<citta.size()-1; i++) {
			int cont=0;
			int j=i;
			while(parziale.get(j+1).getNome().compareTo(parziale.get(j).getNome())==0) {
				cont++;
				j++;
			}
			if(cont<3) {
				if(j==parziale.size() || j==(parziale.size()-1) ) {
					return true;
				} else return false;
				
			}
			if(cont>=3 ) {
				i=j;
			}
		}
		return false;
	}

	private int contaCitta(List<Citta> parziale, String citta) {
		int cont=0;
	
		for(Citta c:parziale) {
			if(c.getNome().compareTo(citta)==0){
				cont++;
			}
		}
		return cont;
	}

}
