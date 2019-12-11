package com.excilys.cdb.ui;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.persistence.MySQLAccess;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;


// old
public class Terminal {


	public static void main(String[] args) {
		
		MySQLAccess connectionDatabase = new MySQLAccess();
		ComputerService computerService = new ComputerService(new ComputerDAO(connectionDatabase));
		CompanyService companyService = new CompanyService(new CompanyDAO(connectionDatabase));

		int x = 1;
		Scanner scanner;
		int intScanner;
		
		try {
			do {
	
					System.out.println("\n\n");
					System.out.println("1 - List computers");
					System.out.println("2 - List companies");
					System.out.println("3 - Detailed information of one computer");
					System.out.println("4 - Create a computer");
					System.out.println("5 - Update a computer");
					System.out.println("6 - Delete a computer");
					System.out.println("0 - Exit");
					System.out.print("Your choice : ");
					
					Scanner sc = new Scanner(System.in);
					x = sc.nextInt();

					switch (x) {
					
					case 1:
						computerService.getListComputer();
						for (Computer computer : computerService.getListComputer()) {
							System.out.println(computer);
						}
						break;
						
					case 2:
						companyService.getListCompany();
						break;
						
					case 3:
						System.out.print("Computer id : ");
						Scanner scc = new Scanner(System.in);
						int inId = scc.nextInt();
						Computer comp = computerService.getComputerById(inId);
						System.out.println(comp);
						
						break;
					case 4:
						if(
							computerService.newComputer(
								new Computer("test05",
										LocalDateTime.now(),  
										LocalDateTime.now(), 
										new Company(1, "Apple Inc.")))
							){
									System.out.println("Computer inserted");
								}
						else {
							System.out.println("Computer not inserted");
						}
						break;
					case 5:
						// update un ordi
//						date = new Date(System.currentTimeMillis());
//						computerService.updateComputer(new Computer(577, "test02",date,date ,""));
						break;
					case 6:
						// delete un ordi
						System.out.print("Computer id : ");
						Scanner scid = new Scanner(System.in);
						
						System.out.print("are you sure ? y/n : ");
						Scanner scconfirmation = new Scanner(System.in);
						
						if(scconfirmation.toString().equals('y')) {
							computerService.deleteComputer(575);
						}
						
						break;
					case 7:
						
						System.out.print("Company id : ");
						scanner = new Scanner(System.in);
						intScanner = scanner.nextInt();
						
						System.out.println( "message : " +
							companyService.findCompanyById(intScanner)
								);
						
						break;
					}
			}while(x!=0);
					
				}
				catch (Exception e) {
					System.err.println(e.getMessage());
				}
				finally {
					// Close database
					companyService.close();
					computerService.close();
					connectionDatabase.close();
				}
	
		
	}

}