package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
	
	private static final String FIND_ALL_COMPANYS = "select id, name"
    												+ " from company;";
	
	
	private static CompanyDAO INSTANCE = null;
	
	
	private CompanyDAO() {
		this.mySQLAccess = MySQLAccess.getInstance();
		this.connect = this.mySQLAccess.getConnect();
	}
	
	public static CompanyDAO getInstance() {
		if (INSTANCE == null) INSTANCE = new CompanyDAO();
		return INSTANCE;
		
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
			if(resultSet.next()){
				
				company = new Company.CompanyBuilder()
						.idCompany(resultSet.getInt("id"))
		        		.nameCompany(resultSet.getString("name"))
		        		.build();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(company);
		
	}
	
	
	public List<Company> getListCompany() {
		
		List<Company> list = new ArrayList<Company>();
		
		try {
			statement = connect.createStatement();
			
			resultSet = statement
                    .executeQuery(FIND_ALL_COMPANYS);
			
		      while (resultSet.next())
		      {
		        Company c = new Company.CompanyBuilder()
						.idCompany(resultSet.getInt("id"))
		        		.nameCompany(resultSet.getString("name"))
		        		.build();
		        list.add(c);
		      }
		} catch (Exception e) {
			e.printStackTrace();
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
