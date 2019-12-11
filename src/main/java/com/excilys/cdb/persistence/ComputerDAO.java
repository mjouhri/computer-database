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
	
	private static final String FIND_ONE_COMPUTER = "select ct.id, ct.name, ct.introduced, ct.discontinued, "
											    		+ "ct.company_id "
											    		+ "from computer ct"
											    		+ "and ct.company_id = ? ";
	private static final String NEW_COMPUTER = 		"insert into computer (name, introduced, discontinued, company_id)"
			   + " values (?, ?, ?, ?)";
	private static final String UPDATE_COMPUTER =  	"UPDATE computer "
											      		+ "SET name = ?,"
											      		+ "introduced = ?, "
											      		+ "discontinued = ?,"
											      		+ "company_id = ?"
											      		+ " WHERE id = ?";
	private static final String DELETE_COMPUTER = 	"delete from computer where id = ?";;

	
	public ComputerDAO() {
		this.mySQLAccess = new MySQLAccess();
		this.connect = this.mySQLAccess.getConnect();
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
		        
		        Computer c = new Computer(resultSet.getInt("id"),
		        		resultSet.getString("name"), 
		        		resultSet.getTimestamp("introduced")==null?
				        		null:resultSet.getTimestamp("introduced").toLocalDateTime(), 
				        resultSet.getTimestamp("discontinued")==null?
						        null:resultSet.getTimestamp("discontinued").toLocalDateTime(),
		        		 new Company(resultSet.getInt("company_id"),
		        				 resultSet.getString("company_name")));
		     
		        list.add(c);   
		      }
		      
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	
	public Optional<Computer> getComputerById(int id) {
		
		Computer computer = null; 
		
		try(PreparedStatement preparedStatement= connect.prepareStatement(FIND_ONE_COMPUTER);
				
				) {
			
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			resultSet.next();
		      
			Computer c = new Computer(resultSet.getInt("id"),
	        		resultSet.getString("name"), 
	        		resultSet.getTimestamp("introduced")==null?
			        		null:resultSet.getTimestamp("introduced").toLocalDateTime(), 
			        resultSet.getTimestamp("discontinued")==null?
					        null:resultSet.getTimestamp("discontinued").toLocalDateTime(),
	        		 new Company(resultSet.getInt("company_id"),
	        				 resultSet.getString("company_name")));
			computer = c;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Optional.of(computer);
	}
	
	public boolean newComputer(Computer computer) {
		try {
			   String query = "insert into computer (name, introduced, discontinued, company_id)"
					   + " values (?, ?, ?, ?)";
			   
			     PreparedStatement preparedStmt = connect.prepareStatement(query);
			     preparedStmt.setString (1, computer.getName());
			     preparedStmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			     preparedStmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			     //preparedStmt.setString(4, null);
			     if ( computer.getCompany() !=null ) preparedStmt.setInt(4, computer.getId());
			     else preparedStmt.setString(4, null);
//			     preparedStmt.setInt(4, computer!=null?computer.getId():null);
			     preparedStmt.execute();
			     
			     
			     return true;
			      
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void updateComputer(Computer computer) {
		
		try {
			// create the java mysql update preparedstatement
		      String query = ""
		      		+ "UPDATE computer "
		      		+ "SET name = ?,"
		      		+ "introduced = ?, "
		      		+ "discontinued = ?,"
		      		+ "company_id = ?"
		      		+ " WHERE id = ?";
		      PreparedStatement preparedStmt = connect.prepareStatement(query);  
		      preparedStmt.setString(1, computer.getName());
//		      preparedStmt.setDate(2, computer.getIntroduced());
//		      preparedStmt.setDate(3, computer.getDiscontinued());
		      preparedStmt.setInt (4, 1);
		      preparedStmt.setInt   (5, computer.getId());
		      
		     
		      // execute the java preparedstatement
		      preparedStmt.executeUpdate();
		      System.out.println("here");
			

			
			
		} catch (Exception e) {
			System.err.println("computerDAO:update : "+e.getMessage());
		}
		
	}
	
	public void deleteComputer(int id) {
			
			try {
				
				String query = "delete from computer where id = ?";
			      PreparedStatement preparedStmt = connect.prepareStatement(query);
			      preparedStmt.setInt(1, id);

			      // execute the preparedstatement
			      preparedStmt.execute();
				
				
				
			} catch (Exception e) {
				System.err.println("computerDAO:deleteComputer : "+e.getMessage());
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
