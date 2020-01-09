package com.excilys.cdb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;


@Controller
public class ComputerController  {
	
	private static final Logger LOG = LoggerFactory.getLogger(ComputerController.class);
	
	private int nbComputers = 0;
	private int sizePage = 10;
	private int nbPages = 0;
	private int page = 0;
	private List<Computer> listComputer;
	
	@Autowired
	private ComputerService computerService;

	@RequestMapping(path= "/",method=RequestMethod.GET)    
    public String dashboard(HttpServletRequest request){
		
		LOG.info("ComputerController:dashboard:doGet ...");
		
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
        
        return "dashboard";
    }
	
	
	
}
