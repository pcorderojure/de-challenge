package com.walmart.org;

public class DTOGame 
{	
	private int id_game;
	private String name_game;
	private int id_console;

	public DTOGame (int id_juego, String nombre_juego, int id_consola) 
	{
		this.id_game = id_juego;
		this.name_game = nombre_juego;
		this.id_console = id_consola;
	}		
	
	public int getId_game() {
		return id_game;
	}
	
	public void setId_game(int id_game) {
		this.id_game = id_game;
	}
	
	public String getName_game() {
		return name_game;
	}
	
	public void setName_game(String name_game) {
		this.name_game = name_game;
	}
	
	public int getId_console() {
		return id_console;
	}
	
	public void setId_console(int id_console) {
		this.id_console = id_console;
	}
}
