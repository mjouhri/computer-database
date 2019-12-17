package com.excilys.cdb.ui;

import java.time.LocalDateTime;
import java.util.Scanner;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.DatabaseConnection;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;


public class Terminal {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Terminal.class);	

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		

		LOGGER.info("Start ...");
		
		DatabaseConnection connectionDatabase = DatabaseConnection.getInstance();
		ComputerService computerService = new ComputerService(new ComputerDAO(connectionDatabase));
		CompanyService companyService = new CompanyService(new CompanyDAO(connectionDatabase));

		int x;
		Scanner scanner;
		int intScanner;
		
		try {
			do {
	
					System.out.println();
					System.out.println("1 - List computers");
					System.out.println("2 - List companies");
					System.out.println("3 - Detailed information of one computer");
					System.out.println("4 - Create a computer");
					System.out.println("5 - Update a computer");
					System.out.println("6 - Delete a computer");
					System.out.println("7 - Detailed information of one company");
					System.out.println("8 - Get Page");
					System.out.println("0 - Exit");
					System.out.print("Your choice : ");
					
					
					scanner = new Scanner(System.in);
					x = scanner.nextInt();

					switch (x) {
					
					case 1:
						for (Computer computer : computerService.getListComputer()) {
							System.out.println(computer);
						}
						break;
						
					case 2:
						for (Company company : companyService.getListCompany()) {
							System.out.println(company);
						}
						break;
						
					case 3:
						System.out.print("Computer id : ");
						scanner = new Scanner(System.in);
						intScanner = scanner.nextInt();
						Computer comp = computerService.getComputerById(intScanner);
						System.out.println(comp);
						
						break;
					case 4:
						if(
							computerService.newComputer(
									
								new Computer.ComputerBuilder()
									.setName("test06")
									.setIntroduced(LocalDateTime.now())
									.setDiscontinued(LocalDateTime.now())
									.setCompany(new Company.CompanyBuilder()
													.idCompany(1)
													.nameCompany("Apple Inc.")
													.build())
									.build()
								)
							
						){
									System.out.println("Computer inserted");
								}
						else {
							System.out.println("Computer not inserted");
						}
						break;
					case 5:
						computerService.updateComputer(
								
								new Computer.ComputerBuilder()
								.setName("test06")
								.setIntroduced(LocalDateTime.now())
								.setDiscontinued(LocalDateTime.now())
								.setCompany(new Company.CompanyBuilder()
												.idCompany(1)
												.nameCompany("Apple Inc.")
												.build())
								.build()

						);
						break; 
					case 6:
						System.out.print("Computer id : ");
						scanner = new Scanner(System.in);
						intScanner = scanner.nextInt();
						computerService.deleteComputer(intScanner);		
						break;
					case 7:
						
						System.out.print("Company id : ");
						scanner = new Scanner(System.in);
						intScanner = scanner.nextInt();
						
						System.out.println(companyService.findCompanyById(intScanner));
						
						break;
					
					case 8:
						System.out.print("Computer id : ");
						scanner = new Scanner(System.in);
						intScanner = scanner.nextInt();
						
						for (Computer computer : computerService.getPage(intScanner, 20)) {
							System.out.println(computer);
						}
						
						break;
					
						
					}
			}while(x!=0);
					
				}
				catch (Exception e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				}
				finally {
					connectionDatabase.close();
					LOGGER.info("End .");
				}
		
	
		
	}

}