package com.excilys.cdb.persistence;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	private MySQLAccess mySQLAccess;
	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	private static final String FIND_ALL_COMPUTERS = "select ct.id, ct.name, ct.introduced, ct.discontinued, "
											    		+ "ct.company_id, cn.name as company_name"
											    		+ " from computer ct, company cn"
											    		+ " where ct.company_id = cn.id;";
	
	private static final String FIND_ONE_COMPUTER = "select id, name, introduced, discontinued, "
											    		+ "company_id"
											    		+ " from computer"
											    		+ " where id = ? ";
	
	private static final String NEW_COMPUTER 	= 		"insert into computer (name, introduced, discontinued, company_id)"
														+ " values (?, ?, ?, ?)";
	
	private static final String UPDATE_COMPUTER =  	"UPDATE computer "
											      		+ "SET name = ?,"
											      		+ "introduced = ?, "
											      		+ "discontinued = ?,"
											      		+ "company_id = ?"
											      		+ " WHERE id = ?";
	private static final String DELETE_COMPUTER = 	"delete from computer where id = ?";;

	
	public static ComputerDAO INSTANCE = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);	

	
	private ComputerDAO() {
		this.mySQLAccess = MySQLAccess.getInstance();
		this.connect = this.mySQLAccess.getConnect();
	}
	
	public static ComputerDAO getInstance() {
		LOGGER.info("getInstance ComputerDAO");

		if (INSTANCE == null) INSTANCE = new ComputerDAO();
		return INSTANCE;
	}

	public ComputerDAO(MySQLAccess mySQLAccess) {
		super();
		this.mySQLAccess = mySQLAccess;
		connect = this.mySQLAccess.getConnect();
	}
	
	public List<Computer> getListComputer() {
		
		List<Computer> list = new ArrayList<Computer>();
		
		try(PreparedStatement preparedStmt = connect.prepareStatement(FIND_ALL_COMPUTERS);
				ResultSet resultSet = preparedStmt.executeQuery();) {
			
		      while (resultSet.next())
		      {
		        
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
										.nameCompany(null)
										.build())
						.build();
		        		 		 		     
		        list.add(c);   
		      }
		      
		      
			LOGGER.info("success get list computers ");

		      
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("failed get list computers ");
		}

		return list;
	}
	
	
	public Optional<Computer> getComputerById(int id) {
		
		Computer computer = null; 
		
		try(PreparedStatement preparedStatement= connect.prepareStatement(FIND_ONE_COMPUTER);		
				) {
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				computer = new Computer.ComputerBuilder()
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
										.nameCompany(null)
										.build())
						.build();


			}
			
			LOGGER.info("success get computer by id : " + id);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("failed get computer by id : " + id + " : error " + e.getMessage());
		}

		return Optional.ofNullable(computer);
	}
	
	public boolean newComputer(Computer computer) {
		try (PreparedStatement preparedStmt = connect.prepareStatement(NEW_COMPUTER);) {
			     preparedStmt.setString (1, computer.getName());
			     preparedStmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			     preparedStmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			     if ( computer.getCompany() !=null ) {
			    	 	preparedStmt.setInt(4, computer.getCompany().getIdCompany());
			    	 }
			     else preparedStmt.setString(4, null);
			     preparedStmt.execute();
			     
			     LOGGER.info("success creat new computer");
			     return true;
			      
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("failed creat new computer");
			return false;
		}
		
	}
	
	public void updateComputer(Computer computer) {
		
		try (PreparedStatement preparedStmt = connect.prepareStatement(UPDATE_COMPUTER);) {
			  preparedStmt.setString (1, computer.getName());
		      preparedStmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
		      preparedStmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
		      if ( computer.getCompany() !=null ) preparedStmt.setInt(4, computer.getCompany().getIdCompany());
		      else preparedStmt.setString(4, null);
		      preparedStmt.setInt   (5, computer.getId());
		      
		      preparedStmt.executeUpdate();
		      LOGGER.info("success update new computer");
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("failed creat update computer");
		}
		
	}
	
	public void deleteComputer(int id) {
			
			try (PreparedStatement preparedStmt = connect.prepareStatement(DELETE_COMPUTER)){
			      preparedStmt.setInt(1, id);
			      preparedStmt.execute();
			      LOGGER.info("success delete new computer");
				
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("failed delete new computer");
			}
			
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
        	e.printStackTrace();
        }
    }
	
	public MySQLAccess getMySQLAccess() {
		return mySQLAccess;
	}

	public void setMySQLAccess(MySQLAccess mySQLAccess) {
		this.mySQLAccess = mySQLAccess;
	}

	public Connection getConnect() {
		return connect;
	}

	public void setConnect(Connection connect) {
		this.connect = connect;
	}

}
