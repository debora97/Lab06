package it.polito.tdp.meteo.db;

import java.time.Month;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class TestMeteoDAO {

	public static void main(String[] args) {

		MeteoDAO dao = new MeteoDAO();

	//List<Rilevamento> list = dao.getAllRilevamenti();
	//	List<Rilevamento> list = dao.getAllRilevamentiLocalitaMese(Month.APRIL, "Torino");
		// STAMPA: localita, giorno, mese, anno, umidita (%)
		List<Citta> list=dao.getAllCitta();
		/*for (Rilevamento r : list) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		}*/
		for (Citta r : list) {
			//System.out.println(r.getNome());
		}
		for (Citta c : list) {

			c.setRilevamenti(dao.getAllRilevamentiLocalitaMese(Month.APRIL, c.getNome()));
			
		
		for (Rilevamento r : c.getRilevamenti()
				) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		}
		}
	//System.out.println(dao.getAllRilevamentiLocalitaMese(1, "Genova"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(1, "Genova"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Milano"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Torino"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Torino"));
		
	}

}
