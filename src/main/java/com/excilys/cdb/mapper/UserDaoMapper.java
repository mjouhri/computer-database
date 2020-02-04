package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.excilys.cdb.model.User;

public class UserDaoMapper implements RowMapper<User> {

	
	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		User c = new User.UserBuilder()
				.idUser(resultSet.getInt("id"))
				.usernameUser(resultSet.getString("username"))
				.passwordUser(resultSet.getString("password"))
				.build();
		return c;
	}
	
}
