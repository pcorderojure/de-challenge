package com.walmart.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ETLTransformacion 
{
	public ArrayList<DTOCompany> lista_companias = new ArrayList<DTOCompany>();
	public ArrayList<DTOConsole> lista_consolas = new ArrayList<DTOConsole>(); 
	public ArrayList<DTOGame> lista_juegos = new ArrayList<DTOGame>();
	public ArrayList<DTOScore> lista_puntuaciones = new ArrayList<DTOScore>();
	
	public void transformaDatos (HashMap<String, String> datos, String tipo)
	{
		datos.forEach(
	            (llave, valor)
	                -> { 	                	
	                	String [] llave_split = llave.split("(==)");
	                	String [] valor_split = valor.split("(==)");

						if (tipo.equals("compania"))
						{
							String nombre_compania = llave_split[0];
							int id_compania = Integer.valueOf(valor_split[0]);
							
							DTOCompany company = new DTOCompany (id_compania, nombre_compania); 
							lista_companias.add(company);							
						}
						
						else if (tipo.equals("consola"))
						{
							String nombre_consola = llave_split[0];
							int id_compania = Integer.valueOf(valor_split[0]);
							int id_consola = Integer.valueOf(valor_split[1]);
							
							DTOConsole console = new DTOConsole (id_consola, nombre_consola, id_compania);
							lista_consolas.add(console);
						}
						
						else if (tipo.equals("juego"))
						{
							String nombre_juego = llave_split[0];							
							int id_consola = Integer.valueOf(llave_split[1]);
							int id_juego = Integer.valueOf(valor_split[0]);

							DTOGame game = new DTOGame (id_juego, nombre_juego, id_consola);
							lista_juegos.add(game);
						}
						
						else if (tipo.equals("puntuaciones"))
						{
							int id_juego = Integer.valueOf(llave_split[0]);							
							Date fecha = Utils.validaFormatoFecha(llave_split[1]);							
							int id_puntuacion = Integer.valueOf(valor_split[0]);
							double metascore = Double.valueOf(valor_split[1]);
							String userscore = valor_split[2];
							
							DTOScore score = new DTOScore (id_puntuacion, id_juego, metascore, userscore, new java.sql.Date (fecha.getTime()));
							lista_puntuaciones.add(score);
						};

	                });
	}
	
	
	public String resultadoDatosTransformados ()
	{
		String resultado = "";
		resultado =  "   Compañías cargadas    = " + lista_companias.size() + "\n";
		resultado += "   Consolas cargadas     = " + lista_consolas.size() + "\n";
		resultado += "   Juegos cargados       = " + lista_juegos.size() + "\n";
		resultado += "   Puntuaciones cargadas = " + lista_puntuaciones.size();
		return resultado;
	}
	
	
}
