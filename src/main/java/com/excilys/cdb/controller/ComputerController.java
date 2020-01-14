package com.excilys.cdb.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.Validator;


@Controller
@RequestMapping(path= "/")
public class ComputerController  {
	
	private static final Logger LOG = LoggerFactory.getLogger(ComputerController.class);
	
	private int nbComputers = 0;
	private static int sizePage = 10;
	private int nbPagesi = 0;
	private static int pagei = 0;
	private List<Computer> listComputer;
	private List<Company> listCompany;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService ;
	Computer computer ;

	@GetMapping("/")  
    public String dashboard(
    		@RequestParam(required = false, defaultValue = "-1") int page,
    		@RequestParam(required = false, defaultValue = "-1") int nbPages,
    		@RequestParam(required = false, defaultValue = "") String name,
    		@RequestParam(required = false, defaultValue = "") String orderBy,
    		Model model){
		
		System.out.println(">>>>> "+page+":"+nbPages+":"
				+ name+ ":" + orderBy);
		
		if(page != -1) {
			System.out.println("getpage");
			pagei = page;
			pagei = page <= 0 ? 1: pagei;
			pagei =  (pagei >= (Math.ceil((nbComputers/(double)sizePage))) 
					? (int)Math.ceil((nbComputers/(double)sizePage)): pagei);
			listComputer = computerService.getPage(pagei, sizePage);
		}
		
		else if(nbPages != -1) {
			System.out.println("sizePageString");
			sizePage  =nbPages;
			nbComputers = computerService.getNbComputers();
			nbPagesi  = (int) Math.ceil((nbComputers/(double)sizePage));
			listComputer = computerService.getPage(pagei, sizePage);
		}
		
		else if(name != null && !name.isEmpty()) {
			System.out.println("nameSearsh");
			listComputer = computerService.getComputersByName(name);
		}
		else if(orderBy != null && !orderBy.isEmpty()) {
			System.out.println("orderBy");
			listComputer = computerService.getComputersOrderBy(orderBy);
		}
		else {
			System.out.println("else");
			nbComputers = computerService.getNbComputers();
			nbPagesi  = (int) Math.ceil((nbComputers/(double)sizePage));
			listComputer = computerService.getPage(pagei, sizePage);
		}
		
		model.addAttribute("nbComputers", nbComputers);
		model.addAttribute("computers", listComputer);
		model.addAttribute("nbPages", nbPagesi);
		model.addAttribute("page", pagei);		
        
        return "dashboard";
    }
	
	
	@GetMapping("/addcomputer")
	public String search(Model model) {
		
		listCompany = companyService.getListCompany();
		model.addAttribute("listCompany", listCompany);
		return "addComputer";
	}
	
	@PostMapping("/addcomputer")
	public String searchPost(
			@RequestParam(required = false, defaultValue = "") String computerName,
			@RequestParam(required = false, defaultValue = "") String introduced,
			@RequestParam(required = false, defaultValue = "") String discontinued,
			@RequestParam(required = false, defaultValue = "-1") int companyId,
			Model model) {
		
		if(Validator.ValidateAddComputer(computerName, introduced, discontinued)) {
			computerService.newComputer(
					
					new Computer.ComputerBuilder()
									.setName(computerName)
									.setIntroduced(introduced)
									.setDiscontinued(discontinued)
									.setCompany(new Company.CompanyBuilder()
													.idCompany(companyId)
													.nameCompany(null)
													.build())
									.build()
								);
		}
		listCompany = companyService.getListCompany();
		model.addAttribute("listCompany", listCompany);
		
		return "addComputer";
	}
	
	
	@GetMapping("/deletecomputer")
	public String deleteComputer(Model model,
		@RequestParam(required = false, defaultValue = "-1") int idComputer) {
		
		computerService.deleteComputer(idComputer);
		
		return "dashboard";
	
	}
	
	@GetMapping("/edit")
	public String edit(Model model,
			@RequestParam(required = false, defaultValue = "-1") int companyId) {
		
		
		computer = computerService.getComputerById(companyId);
		listCompany = companyService.getListCompany();
		
		model.addAttribute("name", computer.getName());
		model.addAttribute("introduced", computer.getIntroduced());
		model.addAttribute("discontinued", computer.getDiscontinued());
		model.addAttribute("companys", listCompany);
		
		
		return "dashboard";
	
	}
	
	@GetMapping("/delete")
	public String editPost(Model model,
			@RequestParam(required = false, defaultValue = "") String computerName,
			@RequestParam(required = false, defaultValue = "") String introduced,
			@RequestParam(required = false, defaultValue = "") String discontinued,
			@RequestParam(required = false, defaultValue = "-1") int companyId
			) {
		
		if(Validator.ValidateAddComputer(computerName, introduced, discontinued)) {
			computerService.updateComputer(
					
					new Computer.ComputerBuilder()
									.setId(computer.getId())
									.setName(computerName)
									.setIntroduced(introduced)
									.setDiscontinued(discontinued)
									.setCompany(new Company.CompanyBuilder()
													.idCompany(companyId)
													.nameCompany(null)
													.build())
									.build()
								);
		}
		
		listCompany = companyService.getListCompany();
		model.addAttribute("listCompany", listCompany);
		
		return "dashboard";
	
	}
	
	
	
	
	
}
