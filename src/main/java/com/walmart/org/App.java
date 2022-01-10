package com.walmart.org;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class App 
{	
	
	public static ETLTransformacion transformacion = null;	
	public static ETLCarga carga_datos = null;
	public static ETLExtraccion extraccion = null;
	public static BaseDatos base_datos = null;
	static Config cfg = null;
	static File file_log = null;
    static BufferedWriter output = null;
    
	
	public static void main(String[] args) 
	{
		file_log = new File("log\\" + Utils.entregaFechaLog().replaceAll("/", "_").replaceAll(":", "_") + " log.txt");	
		String host = null, 
				port = null, 
				url_bd = null, 
				user = null, 
				pass = null, 
				archivo_consolas = null, 
				archivo_resultados = null;
		
		Instant before, 
				after;
		
		long delta;
		
		//Genera archivo de log
		try {
	        output = new BufferedWriter(new FileWriter(file_log));
			Utils.registralog ("Archivo de log generado correctamente", output, false, true);
	
		} catch (Exception em)
		{
			System.out.println ("No se puede escribir archivo de log, verifique si existe la ruta \"log\"");
			salir();
		}
		
		//Lee archivo de parámetros
		try {     
			cfg = new Config();
			host = cfg.getProperty("host_bd");
			port = cfg.getProperty("port_bd");
			url_bd = "jdbc:mysql://" + host + ":" + port + "?allowMultiQueries=true";
			user = cfg.getProperty("user_bd");
			pass = cfg.getProperty("pass_bd");
			archivo_consolas = cfg.getProperty("archivo_consolas");
			archivo_resultados = cfg.getProperty("archivo_resultados");
			
			Utils.registralog ("Archivo de configuración leído correctamente", output, false, true);
		
		} catch (Exception lm)
		{
			Utils.registralog ("No se puede leer archivo de configuración, verifique si existe el archivo en \"config//parameters.config\"", output, true, true);
			salir();
		}
			
		
		try
		{		
			// [ EXTRACCIÓN Y LIMPIEZA ]
			Utils.registralog ("[ EXTRACCIÓN Y LIMPIEZA ] ", output, true, true);
			Utils.registralog ("Inicio de lectura de archivo consolas", output, true, true);
			
			before = Instant.now();
			extraccion = new ETLExtraccion();
			
			String carga_consolas = extraccion.leeArchivoConsolas("data//" + archivo_consolas);
			if (carga_consolas == null)
			{
				Utils.registralog ("Se detectaron errores de carga archivo consolas, revisar log", output, true, true);
				Utils.registralog (extraccion.entregaErroresCarga("consolas"), output, true, false);
				salir();
			}
			Utils.registralog (carga_consolas, output, true, true);
			Utils.registralog (extraccion.entregaErroresCarga("consolas"), output, false, false);
				
			
			Utils.registralog ("Inicio de lectura de archivo resultados", output, true, true);
			String carga_resultados = extraccion.leeArchivoResultados("data//" + archivo_resultados);
			if (carga_resultados == null)
			{
				Utils.registralog ("Se detectaron errores de carga archivo resultados, revisar log", output, true, true);
				Utils.registralog (extraccion.entregaErroresCarga("resultados"), output, true, false);
				salir();
			}
			Utils.registralog (carga_resultados, output, true, true);
			Utils.registralog (extraccion.entregaErroresCarga("resultados"), output, false, false);	
					
			after = Instant.now();
			delta = Duration.between(before, after).toMillis();
			Utils.registralog ("Tiempo extracción y limpieza = " + delta + " ms", output, true, true);
			
			
			Utils.registralog (extraccion.generaArchivosErroresCarga("consolas"), output, true, true);
			Utils.registralog (extraccion.generaArchivosErroresCarga("resultados"), output, true, true);
			
			// [ TRANSFORMACIÓN ]
			Utils.registralog ("[ TRANSFORMACIÓN ]", output, true, true);
			before = Instant.now();
			
			transformacion = new ETLTransformacion();
			transformacion.transformaDatos(extraccion.companias_hash, "compania");
			transformacion.transformaDatos(extraccion.consolas_hash, "consola");
			transformacion.transformaDatos(extraccion.game_hash, "juego");
			transformacion.transformaDatos(extraccion.score_hash, "puntuaciones");
			Utils.registralog (transformacion.resultadoDatosTransformados(), output, true, true);
					
			after = Instant.now();
			delta = Duration.between(before, after).toMillis();
			Utils.registralog ("Tiempo de transformación = " + delta + " ms", output, true, true);
			
			
			// [ CARGA BASE DE DATOS ]
			Utils.registralog ("[ CARGA BASE DE DATOS ]", output, true, true);
			before = Instant.now();
			
			carga_datos = new ETLCarga();
			base_datos = new BaseDatos(url_bd, user, pass);
			boolean BD_creada = base_datos.recrearBD();
			
			if (BD_creada)
			{		
				System.out.println ("   Creación base de datos = " + BD_creada);
				boolean BD_iniciada =base_datos.iniciarBaseDatos();		
				System.out.println ("   Inicio base datos = " + BD_iniciada);		
				boolean companias_cargadas = ETLCarga.cargaCompanias();
				System.out.println ("   Carga compañías = " + companias_cargadas);
				boolean consolas_cargadas = ETLCarga.cargaConsolas();
				System.out.println ("   Carga consolas = " + consolas_cargadas);
				boolean juegos_cargados = ETLCarga.cargaJuegos();
				System.out.println ("   Carga juegos = " + juegos_cargados);
				boolean puntajes_cargados = ETLCarga.cargaPuntajes();
				System.out.println ("   Carga puntajes = " + puntajes_cargados);
				
				if (BD_iniciada 
						&& consolas_cargadas 
						&& juegos_cargados 
						&& puntajes_cargados)
				{
					after = Instant.now();
					delta = Duration.between(before, after).toMillis();
					Utils.registralog ("Tiempo de carga a BD = " + delta + " ms", output, true, true);
				}
				else
				{
					Utils.registralog ("Problemas en la carga de registros a base de datos", output, true, true);
					salir();
				}
			}
			else
			{
				Utils.registralog ("Problemas en la creación de la base de datos", output, true, true);
				salir();
			}
			
			// [ REPORTES ]
			Utils.registralog ("[ REPORTES ]", output, true, true);
			before = Instant.now();	
			
			ETLGeneraReportes.generaTopGameConsolas();
			ETLGeneraReportes.generaWorstGameConsolas();
			ETLGeneraReportes.generaTopGames();
			ETLGeneraReportes.generaWorstGames();
			after = Instant.now();
			delta = Duration.between(before, after).toMillis();
			Utils.registralog ("Tiempo de generación de reportes = " + delta + " ms", output, true, true);
			Utils.registralog ("Reportes generados en carpeta \"report\"", output, true, true);
		
			salir();
		}
		catch (Exception e)
		{
			salir();
		}
	}
	
	private static void salir () 
	{
		try 
		{
			output.flush();
			output.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
