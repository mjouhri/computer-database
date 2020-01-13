package com.excilys.cdb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	private int nbPages = 0;
	private static int page = 0;
	private List<Computer> listComputer;
	private List<Company> listCompany;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService ;
	Computer computer ;

	@RequestMapping(path= "/",method=RequestMethod.GET)    
    public String dashboard(HttpServletRequest request){
		
		System.out.println("in   .... ");
		
		String getpage  = request.getParameter("page");
		String sizePageString  = request.getParameter("nbPages");
		String nameSearsh  = request.getParameter("name");
		String orderBy  = request.getParameter("orderBy");
		
		
		if(getpage!=null && !getpage.isEmpty()) {
			
			page = Integer.parseInt(getpage);
			
			page = page <= 0 ? 1: page;
			page =  (page >= (Math.ceil((nbComputers/(double)sizePage))) 
					? (int)Math.ceil((nbComputers/(double)sizePage)): page);
			listComputer = computerService.getPage(page, sizePage);
		}
		
		else if(sizePageString != null && !sizePageString.isEmpty()) {
			sizePage  = Integer.parseInt(sizePageString);
			nbComputers = computerService.getNbComputers();
			nbPages  = (int) Math.ceil((nbComputers/(double)sizePage));
			listComputer = computerService.getPage(page, sizePage);
		}
		
		else if(nameSearsh != null && !nameSearsh.isEmpty()) {		
			listComputer = computerService.getComputersByName(nameSearsh);
		}
		else if(orderBy != null && !orderBy.isEmpty()) {
			
			listComputer = computerService.getComputersOrderBy(orderBy);
		}
		else {
			
			nbComputers = computerService.getNbComputers();
			nbPages  = (int) Math.ceil((nbComputers/(double)sizePage));
			listComputer = computerService.getPage(page, sizePage);
		}
		
		request.setAttribute("nbComputers", nbComputers);
		request.setAttribute("computers", listComputer);
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("page", page);
		
		System.out.println("out   .... ");
        
        return "dashboard";
    }
	
	@RequestMapping(path= "/addcomputer", method=RequestMethod.GET)
	public String search(HttpServletRequest request) {
		
		listCompany = companyService.getListCompany();
		request.setAttribute("listCompany", listCompany);
		return "addComputer";
	}
	
	@RequestMapping(path= "/addcomputer", method=RequestMethod.POST)
	public String searchPost(HttpServletRequest request) {
		
		String computerName   = request.getParameter("computerName"); 
		String introduced   = request.getParameter("introduced");
		String discontinued   = request.getParameter("discontinued"); 
		int companyId  = Integer.parseInt(request.getParameter("companyId"));
		
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
		
		//// gestion des erreurs avec les exceptions
		
		listCompany = companyService.getListCompany();
		request.setAttribute("listCompany", listCompany);
		
		return "addComputer";
	}
	
	@RequestMapping(path= "/deletecomputer", method=RequestMethod.GET)
	public String deleteComputer(HttpServletRequest request) {
		
		int idComputer  = Integer.parseInt(request.getParameter("idComputer"));		
		computerService.deleteComputer(idComputer);
		
		return "dashboard";
	
	}
	
	@RequestMapping(path= "/edit", method=RequestMethod.GET)
	public String edit(HttpServletRequest request) {
		
		int idComputer  = Integer.parseInt(request.getParameter("companyId"));
		
		computer = computerService.getComputerById(idComputer);
		listCompany = companyService.getListCompany();
		
		request.setAttribute("name", computer.getName());
		request.setAttribute("introduced", computer.getIntroduced());
		request.setAttribute("discontinued", computer.getDiscontinued());
		request.setAttribute("companys", listCompany);
		
		
		return "dashboard";
	
	}
	
	@RequestMapping(path= "/delete", method=RequestMethod.GET)
	public String editPost(HttpServletRequest request) {
		
		String computerName   = request.getParameter("computerName"); 
		String introduced   = request.getParameter("introduced");
		String discontinued   = request.getParameter("discontinued"); 
		int companyId  = Integer.parseInt(request.getParameter("companyId"));
		
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
		request.setAttribute("listCompany", listCompany);
		
		return "dashboard";
	
	}
	
	
	
	
	
}
