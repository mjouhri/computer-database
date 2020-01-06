package com.excilys.cdb.persistence;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository
public class ComputerDAO {
	
	private static final String FIND_ALL_COMPUTERS = "select ct.id, ct.name, ct.introduced, ct.discontinued,"
											    		+ " ct.company_id, company.id, company.name as company_name"
											    		+ " from computer ct"
											    		+ " LEFT JOIN company ON ct.company_id = company.id";
	
	private static final String FIND_ONE_COMPUTER = "select ct.id, ct.name, ct.introduced, ct.discontinued,"
										    		+ " ct.company_id, company.id, company.name as company_name"
										    		+ " from computer ct"
										    		+ " LEFT JOIN company ON ct.company_id = company.id"
											    	+ " where id = ? ";
	
	private static final String NEW_COMPUTER 	= 	"insert into computer (name, introduced, discontinued, company_id)"
														+ " values (?, ?, ?, ?)";
	
	private static final String UPDATE_COMPUTER =  	"UPDATE computer "
											      		+ "SET name = ?,"
											      		+ "introduced = ?, "
											      		+ "discontinued = ?,"
											      		+ "company_id = ?"
											      		+ " WHERE id = ?";
	
	private static final String DELETE_COMPUTER = 	"delete from computer where id = ?";
	
	private static final String FIND_PAGE_2 =  "select computer.id, computer.name, computer.introduced, computer.discontinued,"
									    		+ " computer.company_id, company.id, company.name as company_name"
									    		+ " from computer"
									    		+ " LEFT JOIN company ON computer.company_id = company.id"
												+ " LIMIT ?, ?";
	
	private static final String SIZE_TABLE = "SELECT COUNT(*) as nb FROM computer";
	
	private static final String FIND_COMPUTER_BY_NAME = "select ct.id, ct.name, ct.introduced, ct.discontinued,"
    		+ " ct.company_id, company.id, company.name as company_name"
    		+ " from computer ct"
    		+ " LEFT JOIN company ON ct.company_id = company.id"
	    	+ " where ct.name like ? ";

	private static final String ORDER_BY = "select ct.id, ct.name, ct.introduced, ct.discontinued,"
    		+ " ct.company_id, company.id, company.name as company_name"
    		+ " from computer ct"
    		+ " LEFT JOIN company ON ct.company_id = company.id"
    		+ " ORDER BY ";
	
	
	public static ComputerDAO INSTANCE = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);	
	
	DatabaseConnection databaseConnection;
	
	private ComputerDAO(DatabaseConnection databaseConnection) { 
		this.databaseConnection = databaseConnection;
	}

	
	public List<Computer> getListComputer() {
		
		List<Computer> list = new ArrayList<Computer>();
		
		try(
				Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStmt = connect.prepareStatement(FIND_ALL_COMPUTERS);
				ResultSet resultSet = preparedStmt.executeQuery();) {
			
		      while (resultSet.next())
		      {
		        
		    	  Computer c = resultsetToComputer(resultSet);
		        		 		 		     
		        list.add(c);   
		      }
		      
			LOGGER.info("success get list computers ");

		      
		} catch (SQLException e) {
			LOGGER.error("failed get list computers : " + e.getMessage());
		}

		return list;
	}
	 
	
public int getNbComputers() {

		int count = 0;
		
		try(Connection connect = databaseConnection.getConnection();)
		{
			
			PreparedStatement preparedStmt = connect.prepareStatement(SIZE_TABLE);
			ResultSet resultSet = preparedStmt.executeQuery();
		
			if(resultSet.next()) {
				
				count = resultSet.getInt("nb");
				
				LOGGER.info("Size computer table : " + count );	
			}
		      
		} catch (SQLException e) {
				LOGGER.error("failed get list computers " + e.getMessage());
			}

		return count;
	}

	public List<Computer> getPage(int page, int length) {
		
		LOGGER.info("page  : " + page + "  length   : "+length);

		List<Computer> list = new ArrayList<Computer>();
		
		try(Connection connect = databaseConnection.getConnection();)
		{
			
			PreparedStatement preparedStmt = connect.prepareStatement(SIZE_TABLE);
			ResultSet resultSet = preparedStmt.executeQuery();
		
			if(resultSet.next()) {
				
				int count = resultSet.getInt("nb");
				
				LOGGER.info("Size computer table : " + count );
				
				PreparedStatement preparedStmt2 = connect.prepareStatement(FIND_PAGE_2);
				
				int startPage = (length) * (page + 1);
				
				LOGGER.info("startPage  : " + startPage + "  length   : "+length);
				
				preparedStmt2.setInt(1, startPage);
				preparedStmt2.setInt(2, length);
				ResultSet resultSet2 = preparedStmt2.executeQuery();
				
			      while (resultSet2.next())
			      {
			        
			        Computer c = resultsetToComputer(resultSet2);
			        		 		 		     
			        list.add(c);   
			      }
			      
				LOGGER.info("success get list computers ");
				
			}
		      
		} catch (SQLException e) {
			LOGGER.error("failed get list computers " + e.getMessage());
		}

		return list;
	}

	private Computer resultsetToComputer(ResultSet resultSet) throws SQLException {
		Computer c = new Computer.ComputerBuilder()
				.setId(resultSet.getInt("id"))
				.setName(resultSet.getString("name"))
				.setIntroduced(resultSet
									.getTimestamp("introduced")==null?
									null:resultSet.getTimestamp("introduced")
									.toLocalDateTime())
				.setDiscontinued(resultSet
								.getTimestamp("discontinued")==null?
								null:resultSet.getTimestamp("discontinued")
								.toLocalDateTime())
				.setCompany(new Company.CompanyBuilder()
								.idCompany(resultSet.getInt("company_id"))
								.nameCompany(resultSet.getString("company_name"))
								.build())
				.build();
		return c;
	}
	
	
	public Optional<Computer> getComputerById(int id) {
		Computer computer = null; 
		
		try(Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStatement= connect.prepareStatement(FIND_ONE_COMPUTER);		
				) {
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				computer = resultsetToComputer(resultSet);
			}
				
			LOGGER.info("success get computer by id : " + id);
		} catch (SQLException e) {
			LOGGER.error("failed get computer by id : " + id + " : error " + e.getMessage());
		}

		return Optional.ofNullable(computer);
	}
	
	public boolean newComputer(Computer computer) {
		try (
				Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStmt = connect.prepareStatement(NEW_COMPUTER);) {
			     preparedStmt.setString (1, computer.getName());
			     
			     preparedStmt.setTimestamp(2, computer.
			    		 	getIntroduced() ==null?null:Timestamp.valueOf(computer.getIntroduced())
			    		 );
			     preparedStmt.setTimestamp(3, computer.getDiscontinued() ==null?null:Timestamp
			    		 .valueOf(computer.getDiscontinued())
			    		 );
			     if ( computer.getCompany() !=null ) {
			    	 	preparedStmt.setInt(4, computer.getCompany().getIdCompany());
			    	 }
			     else preparedStmt.setString(4, null);
			     preparedStmt.execute();
			     
			     LOGGER.info("success creat new computer");
			     return true;
			      
		} catch (SQLException e) {
			LOGGER.error("failed creat new computer"+e.getMessage());
			return false;
		}

		
	}
	
	public void updateComputer(Computer computer) {
		
		try (	Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStmt = connect.prepareStatement(UPDATE_COMPUTER);) {
			  preparedStmt.setString (1, computer.getName());
			  preparedStmt.setTimestamp(2, computer.
		    		 	getIntroduced() ==null?null:Timestamp.valueOf(computer.getIntroduced())
		    		 );
		     preparedStmt.setTimestamp(3, computer.getDiscontinued() ==null?null:Timestamp
		    		 .valueOf(computer.getDiscontinued())
		    		 );
		      if ( computer.getCompany() !=null ) preparedStmt.setInt(4, computer.getCompany().getIdCompany());
		      else preparedStmt.setString(4, null);
		      preparedStmt.setInt   (5, computer.getId());
		      
		      preparedStmt.executeUpdate();
		      LOGGER.info("success update new computer");
			
		} catch (SQLException e) {
			LOGGER.error("failed creat update computer"+e.getMessage());
		}
		
	}
	
	public void deleteComputer(int id) {
		
			try (Connection connect = databaseConnection.getConnection();
					PreparedStatement preparedStmt = connect.prepareStatement(DELETE_COMPUTER)){
				     preparedStmt.setInt(1, id);
				     preparedStmt.execute();
				     LOGGER.info("success delete new computer");
				
			} catch (SQLException e) {
					LOGGER.error("failed delete new computer"+e.getMessage());
			}
			
		}
	
	public List<Computer> getComputersByName(String name){
		
		List<Computer> list = new ArrayList<Computer>();
		
		try(
				Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStmt = connect.prepareStatement(FIND_COMPUTER_BY_NAME);
				
				) {
			preparedStmt.setString (1, "%" + name+ "%");
			ResultSet resultSet = preparedStmt.executeQuery();
			
		      while (resultSet.next())
		      {
		        
		    	  Computer c = resultsetToComputer(resultSet);
		        		 		 		     
		        list.add(c);   
		      }
		      
		      resultSet.close();
		      
			LOGGER.info("success get list computers by name ");
		      
		} catch (SQLException e) {
			LOGGER.error("failed get list computers by name :" + e.getMessage());
		}

		return list;
		
		
	}
	
	
	public List<Computer> getComputersOrderBy(String columnName){
		
		
			List<Computer> list = new ArrayList<Computer>();
		
		try(
				Connection connect = databaseConnection.getConnection();
				PreparedStatement preparedStmt = connect.prepareStatement((ORDER_BY+"ct."+columnName));
				) {
			
			 
		      ResultSet resultSet = preparedStmt.executeQuery();
		      
		      System.out.println(ORDER_BY+"ct."+columnName);
		      
				
			
		      while (resultSet.next())
		      {
		        
		    	  Computer c = resultsetToComputer(resultSet);
		        		 		 		     
		        list.add(c);
		      }
		      
		      System.out.println(list.get(0).getName());
		      
		    
			LOGGER.info("success get list computers ");
			resultSet.close();
		      
		} catch (SQLException e) {
			LOGGER.error("failed get list computers " + e.getMessage());
		}

		return list;
	
	}
	

}
