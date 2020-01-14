package com.excilys.cdb.persistence;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.ComputerDaoMapper;
import com.excilys.cdb.model.Computer;

@Repository
public class ComputerDAO {
	
	JdbcTemplate jdbcTemplate;
	
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
	
	// private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);	
	
	@Autowired
	public ComputerDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Computer> getListComputer() {
		return jdbcTemplate.query(FIND_ALL_COMPUTERS, new ComputerDaoMapper());
	}
	 
	
	public int getNbComputers() {
		return jdbcTemplate.queryForObject(SIZE_TABLE, Integer.class);
	}

	public List<Computer> getPage(int page, int length) {
		int startPage = (length) * (page + 1);
		return jdbcTemplate.query(FIND_PAGE_2, new ComputerDaoMapper(), startPage, length);
	}
	
	
	public Optional<Computer> getComputerById(int id) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_ONE_COMPUTER, new Object[] { id }, new ComputerDaoMapper()));
	}
	
	public boolean newComputer(Computer computer) {	
		return jdbcTemplate.update(NEW_COMPUTER,
				computer.getName(),
				computer.getIntroduced() ==null?null:Timestamp.valueOf(computer.getIntroduced()),
				computer.getDiscontinued() ==null?null:Timestamp.valueOf(computer.getDiscontinued()),
				computer.getCompany()==null?null:computer.getCompany().getIdCompany()
				) > 0;
	}
	
	public boolean updateComputer(Computer computer) {
		return jdbcTemplate.update(UPDATE_COMPUTER,
				computer.getId(),
				computer.getName(),
				computer.getIntroduced() ==null?null:Timestamp.valueOf(computer.getIntroduced()),
				computer.getDiscontinued() ==null?null:Timestamp.valueOf(computer.getDiscontinued()),
				computer.getCompany()==null?null:computer.getCompany().getIdCompany()
				) > 0;
	}
	
	public boolean deleteComputer(int id) {	
		return jdbcTemplate.update(DELETE_COMPUTER,	id) > 0;	
		}
	
	public List<Computer> getComputersByName(String name){
		return  jdbcTemplate.query(FIND_COMPUTER_BY_NAME,new ComputerDaoMapper(), "%" + name+ "%");
	}
	
	
	public List<Computer> getComputersOrderBy(String columnName){
		return  jdbcTemplate.query((ORDER_BY+"ct."+columnName),new ComputerDaoMapper());
		

	}
}
