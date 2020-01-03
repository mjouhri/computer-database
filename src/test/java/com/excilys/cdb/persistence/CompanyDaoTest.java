//package com.excilys.cdb.persistence;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.apache.log4j.BasicConfigurator;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.excilys.cdb.model.Company;
//
//public class CompanyDaoTest { 
//	
//	private CompanyDAO companyDao = CompanyDAO.getInstance();
//	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDaoTest.class);	
//	
//	@BeforeAll
//	public static void beforeAll() {  
//		BasicConfigurator.configure();
//		LOGGER.info("@BeforeAll");
//	}
//	
//	@BeforeEach
//	public void beforeEach() {
//		System.setProperty("test","true");
//		LOGGER.info("@beforeEach");	
//	}
//	
//	@Test
//	public void testGetListCompanies(){
//		List<Company> companies = companyDao.getListCompany();
//		assertThat(companies).isNotEmpty().hasSize(6);
//	}
//	
//	@Test
//	public void testGetByIdCompany(){
//		Company company = companyDao.getCompanyById(1).get();
//		assertThat(company).isEqualTo(new Company(1, "Apple Inc." ));
//	}
//	
//	@Test
//	public void testGetByIdCompanyFailed(){
//		Optional<Company> company = companyDao.getCompanyById(99);
//		assertThat(company.isPresent()).isFalse();
//	}
//	
//	
//	@AfterEach
//	void afterEach() {
//		LOGGER.info("@AfterEach "); 
//		System.setProperty("test","false");
//	}
//	
//	
//}
