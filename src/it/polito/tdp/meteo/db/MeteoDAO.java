package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(Month mese, String localita) {
		
		// voglio solo i primi 15 giorni 
           final String sql = "SELECT Localita, Data, Umidita " + 
           		"FROM situazione " + 
           		"WHERE  localita=? AND MONTH(DATA)=? " + 
           		" ORDER BY data ASC ";
          

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			 st.setInt(2, mese.getValue());
			 st.setString(1, localita);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		
	}

	public Double getAvgRilevamentiLocalitaMese(Month mese, String localita) {
		  final String sql = "SELECT AVG(umidita) as U " + 
		  		"FROM situazione " + 
		  		"WHERE  localita=? AND MONTH(DATA)=? " + 
		  		" ORDER BY data ASC ";
	          

			Double media=0.0;

			try {
				Connection conn = DBConnect.getInstance().getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				 
				 st.setString(1, localita);
				 st.setInt(2, mese.getValue());

				ResultSet rs = st.executeQuery();

				while (rs.next()) {

					media=rs.getDouble("U");
				}

				conn.close();
				return media;

			} catch (SQLException e) {

				e.printStackTrace();
				throw new RuntimeException(e);
			} 
	}
public List<Citta> getAllCitta() {
		
		// voglio solo i primi 15 giorni 
           final String sql = "SELECT DISTINCT Localita " + 
           		"FROM situazione ORDER BY  localita ";
          

		List<Citta> citta = new ArrayList<Citta>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Citta r = new Citta(rs.getString("Localita"));
				citta.add(r);
			}

			conn.close();
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		} 
		
	}


}
