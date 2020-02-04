package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;


public class ComputerDaoMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
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

}
