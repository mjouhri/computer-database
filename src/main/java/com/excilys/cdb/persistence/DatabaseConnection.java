package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.ui.Main;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
	private final static String url = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
	 
	private final static String user = "admincdb";
	 
	private final static String passwd = "qwerty1234";
	    
    private Connection connect = null;
    
    private static DatabaseConnection INSTANCE = null;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);	
    
   
    private static HikariConfig config;// = new HikariConfig("/datasource.properties" );
	private static HikariDataSource ds;// = new HikariDataSource( config );
	private Connection connection;
    
     
    private DatabaseConnection() {
		 
	}
    
    public static DatabaseConnection getInstance() {
    	if (INSTANCE == null) {
    		INSTANCE = new DatabaseConnection(); 
    	}
  
        return INSTANCE; 
    }
    
    
    public Connection getConnect() {
		
		if(connection == null) {
			
			try {
				
				if(System.getProperty("test") != null && System.getProperty("test").equals("true")) {
					config = new HikariConfig("/datasourceh2.properties" );
					ds = new HikariDataSource( config );
					connection = ds.getConnection();
				}
				else 
					config = new HikariConfig("/datasourcemysql.properties" );
					ds = new HikariDataSource( config );
					connection = ds.getConnection();

			} catch (SQLException  e) {
				e.printStackTrace();
			} 
		}
		
		return connection;
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

	

}
