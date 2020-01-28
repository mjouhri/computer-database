package com.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;

@Service
public class CompanyService {
	
	private CompanyDAO companyDAO;

	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public List<Company> getListCompany() {
		return companyDAO.getListCompany(); 
	}
	
	public Company findCompanyById(int id) {
		return companyDAO.getCompanyById(id).orElse(null);
	}
	
	public void deleteCompany(int companyId) {
		companyDAO.deleteCompany(companyId);
	}
	
	
	
	

}
