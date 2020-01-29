package com.excilys.cdb.persistence;

import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.excilys.cdb.mapper.UserDaoMapper;
import com.excilys.cdb.model.User;

@Repository
public class UserDAO {
	
	private static final String FIND_ONE_USER = "select id ,username, password"
    		+ " from user"
    	 	+ " where id = ? " ;
	
	private static final String FIND_BY_USERNAME = "select id ,username, password"
    		+ " from user"
    	 	+ " where username = ? " ;
	
	private static final String NEW_USER= 	"insert into user (id, username, password)"
			+ " values (?, ?, ?, ?)";

	
	JdbcTemplate jdbcTemplate;

	
	@Autowired
	public UserDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Optional<User> getComputerById(int id) {
		System.out.println("UserDAO"+ id);
		return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_ONE_USER, new Object[] { id }, new UserDaoMapper()));
	}
	
	public boolean newUser(User user) {	
		return jdbcTemplate.update(NEW_USER,
				user.getId(),
				user.getUsername(),
				user.getPassword()
				) > 0;
	}
	
	public Optional<User> findUser(String username) {
		return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_USERNAME, new Object[] { username }, new UserDaoMapper()));
	}
	

}
