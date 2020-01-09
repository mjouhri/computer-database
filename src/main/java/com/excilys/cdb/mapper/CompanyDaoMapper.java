package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;

public class CompanyDaoMapper  implements RowMapper<Company>  {
	
	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Company c = new Company.CompanyBuilder()
				.idCompany(resultSet.getInt("id"))
        		.nameCompany(resultSet.getString("name"))
        		.build();
		
		return c;
	}

}
