package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	
	
		private static HikariConfig config = new HikariConfig("/database.properties" );
		private static HikariDataSource ds = new HikariDataSource( config );

		private Connection connection;
		
		public Connection getConnect() {
			
			if(connection == null) {
				
				try {
					connection = ds.getConnection();

				} catch (SQLException  e) {
					e.printStackTrace();
				} 
			}
			
			return connection;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public Connection disconnectDB() {
			if(connection!=null) {
				try {
					connection.close();
					connection=null;
				} catch (SQLException se) {
					for(Throwable e : se) {
						System.err.println("Problèmes rencontrés: " + e);
					}
				}
			}
			return connection;
		}
	
	    
	

}
