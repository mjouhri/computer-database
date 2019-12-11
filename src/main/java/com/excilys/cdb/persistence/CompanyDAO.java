package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class CompanyDAO {
	
	private MySQLAccess mySQLAccess;
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private static final String FIND_ONE_COMPANY = "select id ,name "
    		+ "from company "
    		+ "where id = ? " ;
	
	public CompanyDAO() {
		this.mySQLAccess = new MySQLAccess();
		this.connect = this.mySQLAccess.getConnect();
	}
	
	public CompanyDAO(MySQLAccess mySQLAccess) {
		super();
		this.mySQLAccess = mySQLAccess;
		connect = this.mySQLAccess.getConnect();
	}
	
	public Optional<Company> getCompanyById(int id) {
		
		Company company = null; 
		
		try(PreparedStatement preparedStatement= connect.prepareStatement(FIND_ONE_COMPANY);) {
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.err.println("hello error  2");
			if(resultSet.next()){
				
				company = new Company(resultSet.getInt("id"),
		        		resultSet.getString("name")
						);

			}
			
			System.err.println("hello error after if");
			

		}
		catch (Exception e) {
			System.err.println("hello error");
			e.printStackTrace();
		}
		
		
		return Optional.ofNullable(company);
		
	}
	
	
	public ArrayList<Company> getListCompany() {
		
		ArrayList list = new ArrayList<Company>();
		
		try {
			statement = connect.createStatement();
			
			resultSet = statement
                    .executeQuery("select id, name"
                    		+ " from company;"
                    		);
			
			
			
		      while (resultSet.next())
		      {
		        int id = resultSet.getInt("id");
		        String name = resultSet.getString("name");
		        
		        
		        // recuperer la company
		        Company c = new Company(id, name);
		        System.out.println(c.toString());
		        
		        list.add(c);
		   
		      }
			
		
            
		} catch (Exception e) {
			System.err.println("CompanyDAO:getListCompany : " +e.getMessage());
		}
		
		
		return list;
	}
	
	
	 public void close() {
	        try {
	            if (resultSet != null) {
	                resultSet.close();
	            }

	            if (statement != null) {
	                statement.close();
	            }

	        } catch (Exception e) {

	        }
	    }

}
