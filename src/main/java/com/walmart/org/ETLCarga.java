package com.walmart.org;

public class ETLCarga {
	
	public static boolean cargaCompanias ()
	{
		boolean resultado = App.base_datos.ingresarCompanias(App.transformacion.lista_companias);	
		return resultado;		
	}

	public static boolean cargaConsolas ()
	{
		boolean resultado = App.base_datos.ingresarConsolas(App.transformacion.lista_consolas);
		return resultado;		
	}
	
	public static boolean cargaJuegos ()
	{
		boolean resultado = App.base_datos.ingresarJuegos(App.transformacion.lista_juegos);		
		return resultado;		
	}
	
	public static boolean cargaPuntajes ()
	{
		boolean resultado = App.base_datos.ingresarPuntajes(App.transformacion.lista_puntuaciones);
		return resultado;		
	}
}
