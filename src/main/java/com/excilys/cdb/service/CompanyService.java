package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.MySQLAccess;

public class CompanyService {
	
	private CompanyDAO companyDAO;
	private static CompanyService INSTANCE = null;
	
	private CompanyService() {
		this.companyDAO = CompanyDAO.getInstance();
	}
	
	public static CompanyService getInstance() {
		if(INSTANCE == null) INSTANCE = new CompanyService();
		return INSTANCE;
	}
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public List<Company> getListCompany() {
		return companyDAO.getListCompany(); 
	}
	
	public Company findCompanyById(int id) {
		return companyDAO.getCompanyById(id).orElse(null);
	}
	
	
	
	

}
