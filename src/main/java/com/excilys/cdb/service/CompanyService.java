package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

public class CompanyService {
	
	private CompanyDAO companyDAO;
	
	public CompanyService() {
		this.companyDAO = new CompanyDAO();
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
	
	public void close() {
		companyDAO.close();
	}
	
	
	

}
