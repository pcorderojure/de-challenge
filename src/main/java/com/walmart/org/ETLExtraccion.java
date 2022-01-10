package com.walmart.org;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.text.WordUtils;

public class ETLExtraccion 
{	
	ArrayList<String> lista_consolas_error = new ArrayList<String>();
	ArrayList<String> lista_resultados_error = new ArrayList<String>();
	
    HashMap<String, String> consolas_hash = new HashMap<String, String>();
    HashMap<String, String> companias_hash = new HashMap<String, String>();
    HashMap<String, String> game_hash = new HashMap<String, String>();
    HashMap<String, String> score_hash = new HashMap<String, String>();
    
	public String leeArchivoConsolas (String file_path)
	{
		int nro_linea = 1;
        int ok_procesadas = 0;
        int nok_procesadas = 0;
        
		Path path = Paths.get(file_path);
		
		try 
		{
			BufferedReader bufferedReader = Files.newBufferedReader(path);
			
	        String linea = "";
	        int id_compania = 1;
    		int id_consola = 1;
	             
	        String nombre_consola, nombre_compania;
	        String [] linea_separada;
	        
	        while ((linea = bufferedReader.readLine()) != null)
	        {
	        	try 
	        	{	        		
	        		if (nro_linea > 1)
	        		{
	        			linea_separada = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
	        			
	        			if (linea_separada.length != 2)
	        				throw new IOException("Entrada inválida archivo consolas, se ignora");	
	        				
	        			nombre_consola = Utils.limpiaString(linea_separada[0], true)
	        					.trim()
	        					.toUpperCase();
	        			
	        			nombre_compania = WordUtils.capitalizeFully(
	        					Utils.limpiaString(linea_separada[1], true)
	        					.trim());
	        			
	        			if (nombre_consola != null && !nombre_consola.equals("") &&
	        					nombre_compania != null && !nombre_compania.equals(""))
	        			{        				
	        				String busca_id_compania = companias_hash.get(nombre_compania);
	        				String busca_id_consola = consolas_hash.get(nombre_consola);
	        				
	        				if (busca_id_compania == null) // Si no existe la compañía
	        				{
	        					busca_id_compania = String.valueOf(id_compania++);
	        					companias_hash.put(nombre_compania, busca_id_compania);
	        				}
	        			
	        				if (busca_id_consola == null) // Si no existe la consola
	        				{
	        					String valor_consola = busca_id_compania + "==" + String.valueOf(id_consola++);
	        					consolas_hash.put(nombre_consola, valor_consola);
	        				}
	        				else // Si ya existe la consola, verificar la compañía, puede ser error de datos
	        				{
	        					String compania_consola [] = busca_id_consola.split("(==)");
	        					
	        					if (!compania_consola[0].equals(busca_id_compania))
	        						throw new IOException("La consola ya estaba ingresada a otra compañía, se ignora");
	        					
	        					else
	        						throw new IOException("Registro duplicado, se ignora");
	        				}
		        			ok_procesadas ++;
	        			}
	        			else 
	        				throw new IOException("Entrada inválida archivo consolas, se ignora");
	        		}      		  			
	        	}
	        	catch (Exception ioe)
	        	{
	        		lista_consolas_error.add(Utils.entregaFecha() + "==" + nro_linea + "==" + linea + "==" + ioe);
	        		nok_procesadas ++;
	        	}    
	        	nro_linea ++;	        	
	        }
	        bufferedReader.close();	 	        
		} 
		catch (IOException e) 
		{
    		lista_consolas_error.add(Utils.entregaFecha() + "==0==Error en la lectura del archivo==" + e);
		}
		
		return reporteCarga (--nro_linea, ok_procesadas, nok_procesadas);
	}
	
	
	public String leeArchivoResultados (String file_path)
	{			
		int nro_linea = 1;
        int ok_procesadas = 0;
        int nok_procesadas = 0;

		Path path = Paths.get(file_path);        
		try 
		{
			BufferedReader bufferedReader = Files.newBufferedReader(path);
			
	        String linea = "";
	        int id_game_count = 1;	        
	        int id_score_count = 1;	        
	        String metascore, nombre_juego, nombre_consola, userscore, date;
	             
	        while ((linea = bufferedReader.readLine()) != null)
	        {
	        	try 
	        	{	        		
	        		if (nro_linea > 1)
	        		{
	        			String [] linea_separada = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
	        			
	        			if (linea_separada.length != 5)
	        				throw new IOException("Entrada inválida archivo resultados, se ignora");	
	        			
	        			metascore = linea_separada[0]
	        					.trim()
	        					.toLowerCase();
	        			nombre_juego = WordUtils.capitalizeFully(
	        					Utils.limpiaString(linea_separada[1], false)
	        					.trim());
	        			nombre_consola = Utils.limpiaString(linea_separada[2], true)
	        					.trim()
	        					.toUpperCase();
	        			userscore = linea_separada[3]
	        					.trim()
	        					.toLowerCase();
	        			date = Utils.limpiaString(linea_separada[4], false)
	        					.trim()
	        					.toLowerCase();	        			        			
	        			
	        			if (metascore != null && !metascore.equals("") &&
	        					nombre_juego != null && !nombre_juego.equals("") &&
	        					nombre_consola != null && !nombre_consola.equals("") &&
								userscore != null && !userscore.equals("") &&
								date != null && !date.equals(""))
	        			{
	        				String busca_consola = consolas_hash.get(nombre_consola);
	        				String id_consola = busca_consola.split("(==)")[1];
	        				
	        				//valida que el tipo de dato sea el correcto;
	        				@SuppressWarnings("unused")
							double metascore_double = Double.valueOf(metascore);
	        				
	        				if (Utils.validaFormatoFecha(date) == null)
	        					throw new IOException("Formato de fecha inválido, se ignora");
	        				
	        				String llave_game = nombre_juego + "==" + id_consola;
	        				String id_game = game_hash.get(llave_game);
	        					        				
	        				if (id_game == null)
	        				{
	        					id_game = String.valueOf(id_game_count++);
	        					String valor_game = String.valueOf(id_game);
	        					game_hash.put(llave_game, valor_game);
	        				}
	        				
	        				String llave_score = id_game + "==" + date;
	        				String busca_valor_score = score_hash.get(llave_score);
	        				
	        				if (busca_valor_score == null)
	        				{
	        					String valor_score = String.valueOf(id_score_count++) + "==" + metascore  + "==" +  userscore;
	        					score_hash.put(llave_score, valor_score);
	        				}	        				
	        				else // if exists the same score for game/console combination in the same date there is an diplicated record error
	        				{
	        					throw new IOException("Registro con la misma fecha de evaluación, se ignora");
	        				}	      
	        				ok_procesadas++;
	        			}
	        			else 
	        				throw new IOException("Entrada inválida archivo consolas, se ignora");
	        		}      			
	        	}
	        	catch (NumberFormatException nfe)
	        	{
	        		lista_resultados_error.add(Utils.entregaFecha() + "==" + nro_linea + "==" + linea + "==Error formato metascore - " + nfe);
	        		nok_procesadas ++;
	        	} 
	        	catch (Exception ioe)
	        	{
	        		lista_resultados_error.add(Utils.entregaFecha() + "==" + nro_linea + "==" + linea + "==" + ioe);
	        		nok_procesadas ++;
	        	}    			
	        	nro_linea ++;
	        }
	        bufferedReader.close();
		} 
		catch (IOException e) 
		{
			lista_resultados_error.add(Utils.entregaFecha() + "==0==Error en la lectura del archivo==" + e);
		}	
		
		return reporteCarga (--nro_linea, ok_procesadas, nok_procesadas);
	}
		
	
	private String reporteCarga (int nro_linea, int ok_procesadas, int nok_procesadas)
	{
		String resumen = null;
		if (nro_linea != 0)
		{
			resumen = "Líneas leídas = " + nro_linea + "  |  " +
						"Líneas procesadas  = " + ok_procesadas + "  |  " +
						"Líneas descartadas = " + nok_procesadas;	
			
			if (nok_procesadas > 0)
				resumen = resumen + "  (revise detalle en archivo de log)";
		}		
		return resumen;
	}

	
	public String entregaErroresCarga (String archivo)
	{
		ArrayList<String> lista_errores = null;
		String errores = "";
		
		if (archivo.equals("consolas"))
			lista_errores = lista_consolas_error;
		
		else if (archivo.equals("resultados"))
			lista_errores = lista_resultados_error;			
		
		for (int i=0; i<lista_errores.size(); i++)
		{
			String [] error_split = lista_errores.get(i).split("(==)");
			errores += error_split[0] + " línea: " + error_split[1] + " [" + error_split[2] + "] - " + error_split[3] + "\n";
		}
		return errores;
	}
	
	
	public String generaArchivosErroresCarga (String archivo)
	{
		ArrayList<String> lista_errores = null;
		String output_file = "";
		String log_escritura = null;
		
		if (archivo.equals("consolas"))
		{
			output_file = "data//consoles_not_loaded.csv";
			lista_errores = lista_consolas_error;
		}
		else if (archivo.equals("resultados"))
		{
			output_file = "data//result_not_loaded.csv";
			lista_errores = lista_resultados_error;
		}			
		FileOutputStream out = null;       
        try 
        {
            if (lista_errores.size() != 0)
            {
            	out = new FileOutputStream(output_file);  
                
	            for (int i=0; i<lista_errores.size(); i++)
	    		{
	    			String [] error_split = lista_errores.get(i).split("(==)");
	    			out.write((error_split[2] + "\n").getBytes());
	    		}
	            out.flush();
	            out.close();
            }
            else
            {
            	 File archivo_delete = new File(output_file); 
            	 archivo_delete.delete();
            }
            log_escritura = "Archivo de registros no cargados en " + archivo + " generado";
        }
        catch(Exception e) 
        {
        	log_escritura = "Error de escritura, " + e;
        }		
		return log_escritura;	
	}

}
