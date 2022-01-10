package com.walmart.org;

public class DTOConsole 
{	
	private int id_console;
	private String name_console;
	private int id_company;
	
	public DTOConsole (int id_consola, String nombre_consola, int id_compania) 
	{
		this.id_console = id_consola;
		this.name_console = nombre_consola;
		this.id_company = id_compania;
	}	
	
	public int getId_console() {
		return id_console;
	}
	public void setId_console(int id_console) {
		this.id_console = id_console;
	}
	public String getName_console() {
		return name_console;
	}
	public void setName_console(String name_console) {
		this.name_console = name_console;
	}
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
}
