package com.walmart.org;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils 
{
	
	public static String limpiaString (String campo, boolean full)
	{
		if (campo!=null) 
		{
			campo = campo.replaceAll("\"", "");
			if (full)
				campo = campo.replaceAll(",", "");
		}
		
		return campo;
	}
	
	public static String preparaString (String campo)
	{
		if (campo.contains(",")) 
		{
			campo = "\"" + campo + "\"";
		}		
		return campo;
	}
	
	
	public static Date validaFormatoFecha (String fecha_srt)
	{
		SimpleDateFormat dateFormater = new SimpleDateFormat("MMM d, yyyy", Locale.US);
	    Date fecha = null;
		try 
		{
			fecha = dateFormater.parse(fecha_srt);
			return fecha;
		} 
		catch (ParseException e) 
		{
			return null;
		}
	}
	
	
	public static String formateaFechaSql (String fecha_srt)
	{
		String fecha;
		
		SimpleDateFormat dateFormaterSource = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormaterTarget = new SimpleDateFormat("MMM d, yyyy", Locale.US);
	    try 
		{
			Date fecha_util = dateFormaterSource.parse(fecha_srt);
			fecha = dateFormaterTarget.format(fecha_util);  
		} 
		catch (ParseException e) 
		{
			System.out.println(e);
			return null;
		}		
		
		return fecha;		
	}
	
	
	public static String entregaFecha ()
	{
		Date fecha = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String dateString = sdf.format(fecha);
		return dateString;
	}
	
	
	public static String entregaFechaLog ()
	{
		Date fecha = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String dateString = sdf.format(fecha);
		return dateString;
	}
	
	
	public static String registralog (String entrada, BufferedWriter output, boolean display, boolean disp_hora)
	{
		if (disp_hora)
			entrada = entregaFechaLog() + " " + entrada + "\n";
		else
			entrada = entrada + "\n";
		try 
		{
			output.write(entrada);
			output.flush();
			if (display)
				System.out.print(entrada);
		} 
		catch (IOException e) {
		
		}
		return entrada;
	}
}
