package com.walmart.org;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ETLGeneraReportes 
{

	public static void generaTopGameConsolas ()
	{
		 List<DTOConsole> lista_consolas = App.base_datos.consultarConsolas ();
		 List<DTOReporte> lista_reporte_consolas = new ArrayList<DTOReporte>();
		 List<DTOReporte> lista_reporte = null;
		 
		 for (int i=0; i<lista_consolas.size(); i++)
		 {
			 int id_consola = lista_consolas.get(i).getId_console();
			 lista_reporte = App.base_datos.consultarTopWorstGameConsolas(id_consola, true);
			 lista_reporte_consolas.addAll(lista_reporte);
		 }
		 escribeReporte("Top10forConsole", lista_reporte_consolas);
	}
	
	
	public static void generaWorstGameConsolas ()
	{
		 List<DTOConsole> lista_consolas = App.base_datos.consultarConsolas ();
		 List<DTOReporte> lista_reporte_consolas = new ArrayList<DTOReporte>();
		 List<DTOReporte> lista_reporte = null;
		 
		 for (int i=0; i<lista_consolas.size(); i++)
		 {
			 int id_consola = lista_consolas.get(i).getId_console();
			 lista_reporte = App.base_datos.consultarTopWorstGameConsolas(id_consola, false);
			 lista_reporte_consolas.addAll(lista_reporte);
		 }
		 escribeReporte("Worst10forConsole", lista_reporte_consolas);
	}
	
	
	public static void generaTopGames ()
	{
		 List<DTOReporte> lista_reporte = App.base_datos.consultarTopWorstGames(true);
		 escribeReporte("Top10", lista_reporte);
	}
	
	
	public static void generaWorstGames ()
	{
		 List<DTOReporte> lista_reporte = App.base_datos.consultarTopWorstGames(false);
		 escribeReporte("Worst10", lista_reporte);
	}
	
	
	private static void escribeReporte (String tipo, List<DTOReporte> lista_reporte)
	{			
		File file = new File("report//" + tipo + ".csv");
        FileWriter fr = null;
        BufferedWriter br = null;
        try
        {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);        
            br.write("metascore,name,console,company,userscore,date\n");

            for (int i=0; i<lista_reporte.size(); i++)
			 {
				 DTOReporte reporte = lista_reporte.get(i);
				 br.write((int) Double.parseDouble(reporte.getMetascore()) + ",");
				 br.write(Utils.preparaString(reporte.getName_game()) + ",");
				 br.write(reporte.getName_console() + ",");
				 br.write(reporte.getName_company() + ",");
				 br.write(reporte.getUsercode() + ",");
				 br.write(Utils.preparaString(Utils.formateaFechaSql(reporte.getDate_score())) + "\n");
			 }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                br.close();
                fr.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }	   
	}
	
	
	
}
