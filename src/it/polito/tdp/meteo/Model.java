package it.polito.tdp.meteo;

import java.time.Month;
import java.util.*;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {
	MeteoDAO dao = new MeteoDAO();

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;

	private List<Citta> best;
	private int punteggio_best;
	List<Citta> allCitta = new ArrayList<>(dao.getAllCitta());

	/*public Model() {

	}*/

	// metodo che ti rimanda una lista di tutti i rilevamenti di tutti i primi
	// giorni del mese
	// metodo che mi rimanda una lista di rilevamenti con m t g di quel determinato
	// giorno

	public String getUmiditaMedia(Month mese) {
		String medie = "";
		medie = "Torino: " + dao.getAvgRilevamentiLocalitaMese(mese, "Torino") + "\n" + "Milano: "
				+ dao.getAvgRilevamentiLocalitaMese(mese, "Milano") + "\n" + "Genova: "
				+ dao.getAvgRilevamentiLocalitaMese(mese, "Genova");

		return medie;
	}

	public List<Citta> trovaSequenza(Month mese) {
		// punto2)
		List<Citta> parziale = new ArrayList<Citta> ();

		best = null;
		punteggio_best = 100000;
		for (Citta c : allCitta) {

			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
		cerca(parziale, 0, mese);

		return best;
	}

	private void cerca(List<Citta> parziale, int L, Month mese) {

		if (L == NUMERO_GIORNI_TOTALI) {
			int punteggio = punteggioSoluzione(parziale);
			// controllo se la soluzione mi conviene
		if (punteggio <= punteggio_best || best == null) {
				best = new ArrayList<Citta>(parziale);
				punteggio_best = punteggio;
				// return;
			} // se non mi conviene la scarto (else return ?)
		} else {

			for (Citta provac : allCitta) {

				if (controllaParziale(parziale, provac) == true) {

					parziale.add(provac);
					cerca(parziale, L + 1, mese);
					parziale.remove(parziale.size() - 1);// backtracking
				}
			}
		}

	}

	private int punteggioSoluzione(List<Citta> soluzioneCandidata) {
// metodo che mi calcola il punteggio 
		int score = 0;
		for (int i = 1; i <= NUMERO_GIORNI_TOTALI; i++) {
			Citta c = soluzioneCandidata.get(i - 1);
			int umid = c.getRilevamenti().get(i - 1).getUmidita();
			score += umid;

		}
		for (int giorno = 2; giorno <= NUMERO_GIORNI_TOTALI; giorno++) {
			if (!soluzioneCandidata.get(giorno - 1).equals(soluzioneCandidata.get(giorno - 2))) {
				score += COST;
			}
		}
		return score;
	}

	private boolean controllaParziale(List<Citta> parziale, Citta citta) {
		// controllo se c'è 6 volte
		if (contaCitta(parziale, "Torino") <= 6 && contaCitta(parziale, "Milano") <= 6
				&& contaCitta(parziale, "Genova") <= 6) {
			// controllo se c'è 3 volte di fila
			if (controlloMinimo(parziale, citta) == true) {
				return true;
			}
			

		}
		return false;
	

	}

	private boolean controlloMinimo(List<Citta> parziale, Citta prova) {
		String c = "";
		/*if(parziale.size()==0) {
			return true; 
					
		}
		for (int i = 0; i < parziale.size() - 1; i++) {
			int cont = 0;
			// int j=i;
			c = parziale.get(i).getNome();
			for (int j = i; j < parziale.size() - 1; j++) {
				if (parziale.get(j).getNome().compareTo(c) == 0) {
					cont++;

				} else {
					if (cont < 3) {
						if (j == parziale.size() - 1 || j == (parziale.size() - 2)) {
							return true;
						} else
							return false;

					}
					if (cont >= 3) {
						i = j - 1;
					}

				}

			}
		}
		return false;*/
		if (parziale.size() == 0) // primo giorno
			return true;
		if (parziale.size() == 1 || parziale.size() == 2) { // secondo o terzo giorno: non posso cambiare
			return parziale.get(parziale.size() - 1).equals(prova);
		}
		if (parziale.get(parziale.size() - 1).equals(prova)) // giorni successivi, posso SEMPRE rimanere
			return true;
		// sto cambiando citta
		if (parziale.get(parziale.size() - 1).equals(parziale.get(parziale.size() - 2))
				&& parziale.get(parziale.size() - 2).equals(parziale.get(parziale.size() - 3)))
			return true;

		return false;
	}

	private int contaCitta(List<Citta> parziale, String citta) {
		int cont = 1;

		for (Citta c : parziale) {
			if (c.getNome().compareTo(citta) == 0) {
				cont++;
			}
		}
		return cont;
	}

}
