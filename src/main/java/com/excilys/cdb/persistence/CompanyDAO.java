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
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;

@Repository
public class CompanyDAO {

	
	private static final String FIND_ONE_COMPANY = "select id ,name "
										    		+ "from company "
										    	 	+ "where id = ? " ;
	
	private static final String FIND_ALL_COMPANYS = "select id, name"
    												+ " from company;";
	
	private static final String DELETE_COMPANY_BY_COMPANYID = "delete from company where id = ? ;";
	
	private static final String DELETE_COMPUTERS_BY_COMPANYID = "delete from computer where company_id = ? ;";
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);	
	
	private DatabaseConnection databaseConnection;
	
	
	
	private CompanyDAO(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
	public Optional<Company> getCompanyById(int id) {
		
		Company company=null;
		
		try(Connection connect = databaseConnection.getConnection();
			PreparedStatement preparedStatement= connect.prepareStatement(FIND_ONE_COMPANY);) {
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
			LOGGER.error("faild get company by id : " + e);
		}
		
		return Optional.ofNullable(company);
		
	}
	
	
	public List<Company> getListCompany() {
		
		Connection connect = databaseConnection.getConnection();
		
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
			  LOGGER.error("faild get list companies : " + e);
		} 
		
	
		return list;
	}
	
	public void deleteCompany(int companyId) {
		
		try (	Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStmt = connect.prepareStatement(DELETE_COMPANY_BY_COMPANYID);
				PreparedStatement preparedStmt2 = connect.prepareStatement(DELETE_COMPUTERS_BY_COMPANYID);
				){
			
				connect.setAutoCommit(false);
			
				preparedStmt2.setInt(1, companyId);
				preparedStmt2.execute();
				
				preparedStmt.setInt(1, companyId);
				preparedStmt.execute();
				// rollback
				connect.commit();
		      
				LOGGER.info("success delete company");
			
		} catch (SQLException e) {
				LOGGER.error("failed delete company : " + e);
		}
		
	}

}
