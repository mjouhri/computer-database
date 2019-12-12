package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.ui.Terminal;

public class MySQLAccess {
	private final static String url = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
	 
	private final static String user = "admincdb";
	 
	private final static String passwd = "qwerty1234";
	    
    private Connection connect = null;
    
    private static MySQLAccess INSTANCE = null;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Terminal.class);	
    
   
    
    private MySQLAccess() {
		try {	 
            connect = DriverManager.getConnection(url, user, passwd);
			LOGGER.info("database connected");
			
		} catch (Exception e) {
			LOGGER.info("database not connected, error : " + e.getMessage());
		}
	}
     
    
    public static MySQLAccess getInstance() {
    	if (INSTANCE == null) {
    		LOGGER.info("Instancier la base de donnée");
    		INSTANCE = new MySQLAccess(); 
    	}
  
        return INSTANCE; 
    }
    
    public void close() {
        try {

            if (connect != null) {
                connect.close();
            }

        } catch (Exception e) {
        	System.out.println("Deconnexion error"+e.getMessage());
        }
    }
    
    public Connection getConnect() {
		return connect;
	}


	public void setConnect(Connection connect) {
		this.connect = connect;
	}




	

}
