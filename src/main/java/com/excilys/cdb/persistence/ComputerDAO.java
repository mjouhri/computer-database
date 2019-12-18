package com.excilys.cdb.persistence;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

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
	
	private static final String FIND_PAGE = " LIMIT ?, ?";
	
	private static final String SIZE_TABLE = "SELECT COUNT(*) as nb FROM computer";

	
	public static ComputerDAO INSTANCE = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);	
 
	
	private ComputerDAO() { 
	}
	
	public static ComputerDAO getInstance() {
		LOGGER.info("getInstance ComputerDAO");

		if (INSTANCE == null) INSTANCE = new ComputerDAO();
		return INSTANCE;
	}

	public ComputerDAO(DatabaseConnection mySQLAccess) {
		super();
	}
	
	public List<Computer> getListComputer() {
		
		List<Computer> list = new ArrayList<Computer>();
		
		try(
				Connection connect = DatabaseConnection.getInstance().getConnect();
				PreparedStatement preparedStmt = connect.prepareStatement(FIND_ALL_COMPUTERS);
				ResultSet resultSet = preparedStmt.executeQuery();) {
			
		      while (resultSet.next())
		      {
		        
		    	  Computer c = resultsetToComputer(resultSet);
		        		 		 		     
		        list.add(c);   
		      }
		      
			LOGGER.info("success get list computers ");

		      
		} catch (SQLException e) {
			LOGGER.info("failed get list computers ");
			e.printStackTrace();
		}


		return list;
	}
	 
	
public int getNbComputers() {

		int count = 0;
		
		try(Connection connect = DatabaseConnection.getInstance().getConnect();)
		{
			
			PreparedStatement preparedStmt = connect.prepareStatement(SIZE_TABLE);
			ResultSet resultSet = preparedStmt.executeQuery();
		
			if(resultSet.next()) {
				
				count = resultSet.getInt("nb");
				
				LOGGER.info("Size computer table : " + count );
				
			}
		      
		} catch (SQLException e) {
			LOGGER.info("failed get list computers ");
			e.printStackTrace();
		}

		return count;
	}

	public List<Computer> getPage(int page, int length) {

		List<Computer> list = new ArrayList<Computer>();
		
		try(Connection connect = DatabaseConnection.getInstance().getConnect();)
		{
			
			PreparedStatement preparedStmt = connect.prepareStatement(SIZE_TABLE);
			ResultSet resultSet = preparedStmt.executeQuery();
		
			if(resultSet.next()) {
				
				int count = resultSet.getInt("nb");
				
				LOGGER.info("Size computer table : " + count );
				
				preparedStmt = connect.prepareStatement(FIND_ALL_COMPUTERS 
						+  FIND_PAGE);
				
				int startPage = (length) * (page - 1);
				
				preparedStmt.setInt(1, startPage);
				preparedStmt.setInt(2, length);
				resultSet = preparedStmt.executeQuery();
				
			      while (resultSet.next())
			      {
			        
			        Computer c = resultsetToComputer(resultSet);
			        		 		 		     
			        list.add(c);   
			      }
			      
				LOGGER.info("success get list computers ");
				
			}
		      
		} catch (SQLException e) {
			LOGGER.info("failed get list computers ");
			e.printStackTrace();
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
	
	

	private void closeConnexion(Connection connect) {
		try {
			connect.close();
			LOGGER.info("success : closing database");
		} catch (Exception e2) {
			e2.printStackTrace();
			LOGGER.info("failed : closing database");
		}
	} 
	
	
	public Optional<Computer> getComputerById(int id) {
		
		Connection connect = DatabaseConnection.getInstance().getConnect();
		Computer computer = null; 
		
		try(PreparedStatement preparedStatement= connect.prepareStatement(FIND_ONE_COMPUTER);		
				) {
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				computer = resultsetToComputer(resultSet);
			}
				
			LOGGER.info("success get computer by id : " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.info("failed get computer by id : " + id + " : error " + e.getMessage());
		}
		finally {
			closeConnexion(connect);
		}

		return Optional.ofNullable(computer);
	}
	
	public boolean newComputer(Computer computer) {
		Connection connect = DatabaseConnection.getInstance().getConnect();

		try (PreparedStatement preparedStmt = connect.prepareStatement(NEW_COMPUTER);) {
			     preparedStmt.setString (1, computer.getName());
			     
			     preparedStmt.setTimestamp(2, computer.
			    		 	getIntroduced() ==null?null:Timestamp.valueOf(computer.getIntroduced())
			    		 );
			     preparedStmt.setTimestamp(3, computer.getDiscontinued() ==null?null:Timestamp.valueOf(computer.getDiscontinued())
			    		 );
			     if ( computer.getCompany() !=null ) {
			    	 	preparedStmt.setInt(4, computer.getCompany().getIdCompany());
			    	 }
			     else preparedStmt.setString(4, null);
			     preparedStmt.execute();
			     
			     LOGGER.info("success creat new computer");
			     return true;
			      
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.info("failed creat new computer");
			return false;
		}
		finally {
			closeConnexion(connect);
		}
		
	}
	
	public void updateComputer(Computer computer) {
		Connection connect = DatabaseConnection.getInstance().getConnect();
		try (PreparedStatement preparedStmt = connect.prepareStatement(UPDATE_COMPUTER);) {
			  preparedStmt.setString (1, computer.getName());
			  preparedStmt.setTimestamp(2, computer.
		    		 	getIntroduced() ==null?null:Timestamp.valueOf(computer.getIntroduced())
		    		 );
		     preparedStmt.setTimestamp(3, computer.getDiscontinued() ==null?null:Timestamp.valueOf(computer.getDiscontinued())
		    		 );
		      if ( computer.getCompany() !=null ) preparedStmt.setInt(4, computer.getCompany().getIdCompany());
		      else preparedStmt.setString(4, null);
		      preparedStmt.setInt   (5, computer.getId());
		      
		      preparedStmt.executeUpdate();
		      LOGGER.info("success update new computer");
			
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.info("failed creat update computer");
		}
		finally {
			closeConnexion(connect);
		}
		
	}
	
	public void deleteComputer(int id) {
		Connection connect = DatabaseConnection.getInstance().getConnect();
			try (PreparedStatement preparedStmt = connect.prepareStatement(DELETE_COMPUTER)){
			      preparedStmt.setInt(1, id);
			      preparedStmt.execute();
			      LOGGER.info("success delete new computer");
				
			} catch (SQLException e) {
					e.printStackTrace();
					LOGGER.info("failed delete new computer");
			}
			finally {
				closeConnexion(connect);
			}
			
		}
	

}
