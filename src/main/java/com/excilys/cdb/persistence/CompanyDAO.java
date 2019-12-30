package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;


public class CompanyDAO {

	
	private static final String FIND_ONE_COMPANY = "select id ,name "
										    		+ "from company "
										    	 	+ "where id = ? " ;
	
	private static final String FIND_ALL_COMPANYS = "select id, name"
    												+ " from company;";
	
	private static final String DELETE_COMPANY_BY_COMPANYID = "delete from company where id = ? ;";
	
	private static final String DELETE_COMPUTERS_BY_COMPANYID = "delete from computer where company_id = ? ;";
	
	 
	private static CompanyDAO INSTANCE = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);	
	
	private CompanyDAO() {
	}
	
	public static CompanyDAO getInstance() {
		LOGGER.info("getInstance CompanyDAO");
		
		if (INSTANCE == null) INSTANCE = new CompanyDAO();
		return INSTANCE;
		 
	}
	
	public CompanyDAO(DatabaseConnection mySQLAccess) {
		super();
	}
	
	public Optional<Company> getCompanyById(int id) {
		Connection connect = DatabaseConnection.getInstance().getConnect();
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
			LOGGER.info("success get company by id : " + id);
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnexion(connect);
		}
		
		return Optional.ofNullable(company);
		
	}
	
	
	public List<Company> getListCompany() {
		
		Connection connect = DatabaseConnection.getInstance().getConnect();
		
		List<Company> list = new ArrayList<Company>();
		
		try (PreparedStatement statement = connect.prepareStatement(FIND_ALL_COMPANYS);
				ResultSet resultSet = statement.executeQuery();)
				{
			
		      while (resultSet.next())
		      {
		        Company c = new Company.CompanyBuilder()
						.idCompany(resultSet.getInt("id"))
		        		.nameCompany(resultSet.getString("name"))
		        		.build();
		        list.add(c);
		      }
		      LOGGER.info("success :  get list companies");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			closeConnexion(connect);
		}
	
		return list;
	}
	
	public void deleteCompany(int companyId) {
		
		try (	Connection connect = DatabaseConnection.getInstance().getConnect();
				PreparedStatement preparedStmt = connect.prepareStatement(DELETE_COMPANY_BY_COMPANYID);
				PreparedStatement preparedStmt2 = connect.prepareStatement(DELETE_COMPUTERS_BY_COMPANYID);
				){
			
				connect.setAutoCommit(false);
			
				preparedStmt.setInt(1, companyId);
				preparedStmt.execute();
				
				preparedStmt2.setInt(1, companyId);
				preparedStmt2.execute();
				
				connect.commit();
		      
		      LOGGER.info("success delete company");
			
		} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.info("failed delete company");
		}
		
	}

	private void closeConnexion(Connection connect) {
		try {
			connect.close();
			LOGGER.info("success : closing database");
		} catch (Exception e2) {
			e2.printStackTrace();
			LOGGER.info("failed : closing database");
		}
	}
	

}
