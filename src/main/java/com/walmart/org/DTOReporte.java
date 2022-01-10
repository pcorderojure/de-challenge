package com.walmart.org;

public class DTOReporte {

	private String metascore;
	private String name_game;
	private String name_console;
	private String name_company;
	private String usercode;
	private String date_score;
	
	public DTOReporte (String mscore, String nombre_jurego, String nombre_consola, String nombre_compania, String ucode, String fecha) 
	{
		this.metascore = mscore;
		this.name_game = nombre_jurego;
		this.name_console = nombre_consola;
		this.name_company = nombre_compania;
		this.usercode = ucode;
		this.date_score = fecha;	
	}

	public String getMetascore() {
		return metascore;
	}

	public void setMetascore(String metascore) {
		this.metascore = metascore;
	}

	public String getName_game() {
		return name_game;
	}

	public void setName_game(String name_game) {
		this.name_game = name_game;
	}

	public String getName_console() {
		return name_console;
	}

	public void setName_console(String name_console) {
		this.name_console = name_console;
	}

	public String getName_company() {
		return name_company;
	}

	public void setName_company(String name_company) {
		this.name_company = name_company;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getDate_score() {
		return date_score;
	}

	public void setDate_score(String date_score) {
		this.date_score = date_score;
	}
}
