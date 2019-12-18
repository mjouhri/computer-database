package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.ui.Main;

public class DatabaseConnection {
	private final static String url = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
	 
	private final static String user = "admincdb";
	 
	private final static String passwd = "qwerty1234";
	    
    private Connection connect = null;
    
    private static DatabaseConnection INSTANCE = null;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);	
    
   
     
    private DatabaseConnection() {
		 
	}
    
    public static DatabaseConnection getInstance() {
    	if (INSTANCE == null) {
    		INSTANCE = new DatabaseConnection(); 
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
    	
    	try {	 
			if(System.getProperty("test") != null && System.getProperty("test").equals("true")) {
				String urlTest ="jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:create.sql'";
				String userTest = "";
				String passwdTest = "";
				LOGGER.info("Connexion database h2 ");
				if(connect == null || connect.isClosed()) {
					connect = DriverManager.getConnection(urlTest, userTest, passwdTest);
				}
			
			} else {
				LOGGER.info("Connexion database MySQL ");
	            if(connect == null || connect.isClosed()) {
	            	Class.forName("com.mysql.jdbc.Driver");
					connect = DriverManager.getConnection(url, user, passwd);
				}
				
			}
			LOGGER.info("database connected");
			
		} catch (Exception e) {
			LOGGER.info("database not connected, error : " + e.getMessage());
		}
    	
    	return connect;
	}

	

}
