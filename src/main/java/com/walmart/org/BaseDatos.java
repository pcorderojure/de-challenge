package com.walmart.org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class BaseDatos 
{
	public boolean sck_pass = false;
	String url_bd;
	String username; 
	String password;
	
	Connection conn = null;
	PreparedStatement stmt = null;

	public BaseDatos (String url_bd, String username, String password) 
	{
		this.url_bd = url_bd;
		this.username = username;
		this.password = password;
	}	
	
	
	private boolean conectarBD () {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url_bd, username, password);
			return true;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println ("Hay problemas de conexión con la Base de datos");
		return false;
	}


	private void desconectarBD () {
		try{
    		if(conn !=null)
    			conn.close();
    	} catch(SQLException se) {
    		se.printStackTrace();
    	}
    }
	
	
	public boolean iniciarBaseDatos () 
	{
		if (conectarBD()) {
		    try {
		    	
		    	String db_check = "SHOW DATABASES LIKE 'wm_games'";
		    	stmt = conn.prepareStatement(db_check);
		    	ResultSet rs = stmt.executeQuery(db_check);
		         
		        int n_elementos = 0;
		        while (rs.next())
		        	n_elementos++;
		        if (n_elementos==0)		         
		        	return false;

		    	return true;
		
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	return false;
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return false;
	}
	
	
	public boolean recrearBD () 
	{
		if (conectarBD()) {
		    try {
		    	String script = "" + 
		    			"DROP DATABASE IF EXISTS wm_games;" + 
		    			"" + 
		    			"CREATE DATABASE wm_games;" + 
		    			"USE wm_games;" + 
		    			"" + 
		    			"CREATE TABLE wm_games.company (" + 
		    			"  id_company int(11) PRIMARY KEY NOT NULL," + 
		    			"  name_company varchar(250) NOT NULL" + 
		    			");" + 
		    			"" + 
		    			"CREATE TABLE wm_games.console (" + 
		    			"  id_console int(11) PRIMARY KEY NOT NULL," + 
		    			"  name_console varchar(250) NOT NULL," + 
		    			"  id_company int(11) NOT NULL," + 
		    			"  FOREIGN KEY (id_company) REFERENCES wm_games.company (id_company)" + 
		    			");" + 
		    			"" + 
		    			"CREATE TABLE wm_games.game (" + 
		    			"  id_game int(11) PRIMARY KEY NOT NULL," + 
		    			"  name_game varchar(250) NOT NULL," + 
		    			"  id_console int(11) NOT NULL," + 
		    			"  FOREIGN KEY (id_console) REFERENCES wm_games.console (id_console)" + 
		    			");" + 
		    			"" + 
		    			"CREATE TABLE wm_games.score (" + 
		    			"  id_score int(11) PRIMARY KEY NOT NULL," + 
		    			"  id_game int(11) NOT NULL," + 
		    			"  metascore double NOT NULL," + 
		    			"  usercode varchar(10) DEFAULT NULL," + 
		    			"  date_score date NOT NULL," + 
		    			"  FOREIGN KEY (id_game) REFERENCES wm_games.game (id_game)" + 
		    			");" ;
		    	
		    	Statement stmt = conn.createStatement();
		        stmt.execute(script);
		        return true;
		    	
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return false;
	}
	
	
	public boolean ingresarCompanias (ArrayList<DTOCompany> lista_companias) 
	{
		if (conectarBD()) 
		{			
		    String INSERT_GAMES = "INSERT INTO wm_games.company (id_company, name_company) VALUES " +
		               " (?, ?);";
		    try
		    {
		        PreparedStatement statement = conn.prepareStatement(INSERT_GAMES);
		        int i = 0;
	
		        for (int j = 0; j < lista_companias.size() ; j++ )
		        {
		        	statement.setInt    (1, lista_companias.get(j).getId_company());
		        	statement.setString (2, lista_companias.get(j).getName_company());
		        	statement.addBatch();
		            i++;
	
		            if (i % 1000 == 0 || i == lista_companias.size()) 
		            {
		                statement.executeBatch();
		            }
		        }
		        return true;
		        
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return false;
	}
	
	
	public boolean ingresarConsolas (ArrayList<DTOConsole> lista_consolas) 
	{
		if (conectarBD()) 
		{			
		    String INSERT_GAMES = "INSERT INTO wm_games.console (id_console, name_console, id_company) VALUES " +
		               " (?, ?, ?);";
		    try
		    {
		        PreparedStatement statement = conn.prepareStatement(INSERT_GAMES);
		        int i = 0;
	
		        for (int j = 0; j < lista_consolas.size() ; j++ )
		        {
		        	statement.setInt    (1, lista_consolas.get(j).getId_console());
		        	statement.setString (2, lista_consolas.get(j).getName_console());
		        	statement.setInt    (3, lista_consolas.get(j).getId_company());
		            statement.addBatch();
		            i++;
	
		            if (i % 1000 == 0 || i == lista_consolas.size()) 
		            {
		                statement.executeBatch();
		            }
		        }
		        return true;
		        
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return false;
	}

	
	public boolean ingresarJuegos (ArrayList<DTOGame> lista_juegos) 
	{
		if (conectarBD()) 
		{			
		    String INSERT_GAMES = "INSERT INTO wm_games.game (id_game, name_game, id_console) VALUES " +
		               " (?, ?, ?);";
		    try
		    {
		        PreparedStatement statement = conn.prepareStatement(INSERT_GAMES);
		        int i = 0;
	
		        for (int j = 0; j < lista_juegos.size() ; j++ )
		        {
		        	statement.setInt    (1, lista_juegos.get(j).getId_game());
		        	statement.setString (2, lista_juegos.get(j).getName_game());
		        	statement.setInt    (3, lista_juegos.get(j).getId_console());
		            statement.addBatch();
		            i++;
	
		            if (i % 1000 == 0 || i == lista_juegos.size()) 
		            {
		                statement.executeBatch();
		            }
		        }
		        return true;
		        
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return false;
	}
	
	
	public boolean ingresarPuntajes (ArrayList<DTOScore> lista_puntuaciones) 
	{	
		if (conectarBD()) 
		{			
		    String INSERT_SCORES = "INSERT INTO wm_games.score (id_score, id_game, metascore, usercode, date_score) VALUES " +
		               " (?, ?, ?, ?, ?);";
		    try
		    {
		        PreparedStatement statement = conn.prepareStatement(INSERT_SCORES);
		        int i = 0;
	
		        for (int j = 0; j < lista_puntuaciones.size() ; j++ )
		        {
		        	statement.setInt    (1, lista_puntuaciones.get(j).getId_score());
		        	statement.setInt    (2, lista_puntuaciones.get(j).getId_game());
		        	statement.setDouble (3, lista_puntuaciones.get(j).getMetascore());
		        	statement.setString (4, lista_puntuaciones.get(j).getUsercode());
		        	statement.setDate   (5, lista_puntuaciones.get(j).getDate());
		            statement.addBatch();
		            i++;
	
		            if (i % 1000 == 0 || i == lista_puntuaciones.size()) 
		            {
		                statement.executeBatch();
		            }
		        }
		        return true;
		        
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return false;
	}
	
	
	public List<DTOConsole> consultarConsolas () 
	{
		List<DTOConsole> lista_consolas = null;
		if (conectarBD()) {
		    try {
		    	String consulta = "SELECT * from wm_games.console";
		    	Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(consulta);
		        
		        lista_consolas = new ArrayList<DTOConsole>();
			    
		        while (rs.next()) 
		        {
		        	int id_console = rs.getInt("id_console");
		        	String name_console = rs.getString("name_console");
		        	int id_company = rs.getInt("id_company");
		   
		        	DTOConsole consola = new DTOConsole(id_console, name_console, id_company);
		        	lista_consolas.add(consola);	              
		        }
		         		    	
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return lista_consolas;
	}
	
	
	public List<DTOReporte> consultarTopWorstGameConsolas (int id_console, boolean top) 
	{
		String order = (top) ? "DESC" : "ASC"; 
		List<DTOReporte> lista_reporte = null;
		if (conectarBD()) {
		    try {
		    	String consulta = "	"
		    			+ "select 	s.metascore," + 
		    			"			g.name_game, " + 
		    			"			c.name_console," + 
		    			"			co.name_company," + 
		    			"			s.usercode," + 
		    			"			s.date_score" + 
		    			"" + 
		    			"	from 	wm_games.game g, " + 
		    			"			wm_games.score s, " + 
		    			"			wm_games.console c," + 
		    			"			wm_games.company co" + 
		    			"" + 
		    			"	where 	c.id_console = " + id_console +
		    			"		and g.id_game=s.id_game" + 
		    			"		and g.id_console = c.id_console" + 
		    			"		and c.id_company = co.id_company" + 
		    			"		and s.date_score = (select max(date_score) from wm_games.score where id_game = g.id_game)" + 
		    			"" + 
		    			"	order by s.metascore " + order  + ", s.usercode " + order + 
		    			"" + 
		    			"	LIMIT 10";
		    	
		    	Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(consulta);
		        
		        lista_reporte = new ArrayList<DTOReporte>();
			    
		        while (rs.next()) 
		        {
		        	String metascore = rs.getString("metascore");
		        	String name_game = rs.getString("name_game");
		        	String name_console = rs.getString("name_console");
		        	String name_company = rs.getString("name_company");
		        	String usercode = rs.getString("usercode");
		        	String date_score = rs.getString("date_score");
		   
		        	DTOReporte reporte = new DTOReporte(metascore, name_game, name_console, name_company, usercode, date_score);
		        	lista_reporte.add(reporte);	              
		        }
		         		    	
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return lista_reporte;
	}
	
	
	public List<DTOReporte> consultarTopWorstGames (boolean top) 
	{
		String order = (top) ? "DESC" : "ASC"; 
		List<DTOReporte> lista_reporte = null;
		if (conectarBD()) {
		    try {
		    	String consulta = "	"
		    			+ "	select 	s.metascore," + 
		    			"			g.name_game, " + 
		    			"			c.name_console," + 
		    			"			co.name_company," + 
		    			"			s.usercode," + 
		    			"			s.date_score" + 
		    			"" + 
		    			"	from 	wm_games.game g, " + 
		    			"			wm_games.score s, " + 
		    			"			wm_games.console c," + 
		    			"			wm_games.company co" + 
		    			"" + 
		    			"	where 	g.id_game=s.id_game" + 
		    			"		and g.id_console = c.id_console" + 
		    			"		and c.id_company = co.id_company" + 
		    			"		and s.date_score = (select max(date_score) from wm_games.score where id_game = g.id_game)" + 
		    			"		" + 
		    			"	order by s.metascore " + order + ", s.usercode " + order + 
		    			"	" + 
		    			"	LIMIT 10";
		    	
		    	Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(consulta);
		        
		        lista_reporte = new ArrayList<DTOReporte>();
			    
		        while (rs.next()) 
		        {
		        	String metascore = rs.getString("metascore");
		        	String name_game = rs.getString("name_game");
		        	String name_console = rs.getString("name_console");
		        	String name_company = rs.getString("name_company");
		        	String usercode = rs.getString("usercode");
		        	String date_score = rs.getString("date_score");
		   
		        	DTOReporte reporte = new DTOReporte(metascore, name_game, name_console, name_company, usercode, date_score);
		        	lista_reporte.add(reporte);	              
		        }
		         		    	
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    	
		    } finally {
		    	try {
		    		if(stmt!=null)
		    			stmt.close();
		    	} catch(SQLException se2) {
		    		se2.printStackTrace();
		    	}
		    	desconectarBD();
		    }
		}
		return lista_reporte;
	}
}



