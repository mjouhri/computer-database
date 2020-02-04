package com.excilys.cdb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.model.Company;

@RestController
@RequestMapping(value = "/companys")
public class CompanyController {
	
	private CompanyService companyService ;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@CrossOrigin
	@GetMapping
	public List<Company> getAll() {
		return companyService.getListCompany();
	}


}
