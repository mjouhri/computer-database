package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.CompanyDaoMapper;
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
	
	JdbcTemplate jdbcTemplate;
	
	private CompanyDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Optional<Company> getCompanyById(int id) {
		
		return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_ONE_COMPANY, new Object[] { id }, new CompanyDaoMapper()));
		
	}
	
	
	public List<Company> getListCompany() {
		
		return jdbcTemplate.query(FIND_ALL_COMPANYS, new CompanyDaoMapper());
		
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
