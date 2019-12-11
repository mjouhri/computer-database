package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySQLAccess {
	private final static String url = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
	 
	private final static String user = "admincdb";
	 
	private final static String passwd = "qwerty1234";
	    
    private Connection connect = null;
    
    
    // !!!!!! Singleton !!!!!!
    
    public MySQLAccess() {
		try {	 
            connect = DriverManager.getConnection(url, user, passwd);
			
			System.out.println("connected");
			
		} catch (Exception e) {
			System.out.println("Connexion error : "+e.getMessage());
		}
	}
    
//    private static MySQLAccess INSTANCE = new MySQLAccess();
//    
//    public static MySQLAccess getInstance() {
//    	return INSTANCE;
//    }
    
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
