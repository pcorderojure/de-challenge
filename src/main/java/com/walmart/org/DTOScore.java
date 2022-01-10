package com.walmart.org;

import java.sql.Date;

public class DTOScore 
{	
	private int id_score;
	private int id_game;
	private double metascore;
	private String usercode;
	private Date date;
	
	public DTOScore (int id_puntaje, int id_juego, double mscore, String uscore, Date fecha) 
	{
		this.id_score = id_puntaje;
		this.id_game = id_juego;
		this.metascore = mscore;
		this.usercode = uscore;
		this.date = fecha;
	}

	public int getId_score() {
		return id_score;
	}

	public void setId_score(int id_score) {
		this.id_score = id_score;
	}

	public int getId_game() {
		return id_game;
	}

	public void setId_game(int id_game) {
		this.id_game = id_game;
	}

	public double getMetascore() {
		return metascore;
	}

	public void setMetascore(double metascore) {
		this.metascore = metascore;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}		
}
