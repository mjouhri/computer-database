package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseConnection {
	private final static String url = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
	 
	private final static String user = "admincdb";
	 
	private final static String passwd = "qwerty1234";
	    
    private Connection connect = null;
    
    private static DatabaseConnection INSTANCE = null;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);	
    
//   
//    private static HikariConfig config;// = new HikariConfig("/datasource.properties" );
//	private static HikariDataSource ds;// = new HikariDataSource( config );
	private Connection connection;
	
	private DataSource dataSource;
     
    private DatabaseConnection(DataSource dataSource) {
		 this.dataSource= dataSource;
 
	}
    
    public  Connection getConnection(){
        try {
			if(connection == null || connection.isClosed()) {
				connection = dataSource.getConnection();
				return connection;
			}
		} catch (SQLException e) {
			
		}
        return connection;
	}
    
    public void close() {
        try {

            if (connect != null) {
                connect.close();
            }

        } catch (Exception e) {
        }
    }


}
    
//    public static DatabaseConnection getInstance() {
//    	if (INSTANCE == null) {
//    		INSTANCE = new DatabaseConnection(); 
//    	}
//  
//        return INSTANCE; 
//    }
    
    
//    public Connection getConnectOLD() {
//		
//		if(connection == null) {
//			
//			try {
//				
//				if(System.getProperty("test") != null && System.getProperty("test").equals("true")) {
//					config = new HikariConfig("/datasourceh2.properties" );
//					ds = new HikariDataSource( config );
//					connection = ds.getConnection();
//				}
//				else 
//					config = new HikariConfig("/datasourcemysql.properties" );
//					ds = new HikariDataSource( config );
//					connection = ds.getConnection();
//
//			} catch (SQLException  e) {
//				e.printStackTrace();
//			} 
//		}
//		                           
//		return connection;
//	}
    
    
//    public Connection getConnect()  {
//    	
//    	try {	 
//			if(System.getProperty("test") != null && System.getProperty("test").equals("true")) {
//				String urlTest ="jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:create.sql'";
//				String userTest = "";
//				String passwdTest = "";
//				LOGGER.info("Connexion database h2 ");
//				if(connect == null || connect.isClosed()) {
//					connect = DriverManager.getConnection(urlTest, userTest, passwdTest);
//				}
//			
//			} else {
//				LOGGER.info("Connexion database MySQL ");
//	            if(connect == null || connect.isClosed()) {
//	            	Class.forName("com.mysql.jdbc.Driver");
//					connect = dataSource.getConnection();
//				}
//				
//			}
//			LOGGER.info("database connected");
//			
//		} catch (Exception e) {
//			LOGGER.info("database not connected, error : " + e.getMessage());
//		}
//    	
//    	return connect;
//	}
    
   
