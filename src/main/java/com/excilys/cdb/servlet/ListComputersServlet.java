package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Controller
@WebServlet(name = "dashboard", urlPatterns = {"/dashboard"})
public class ListComputersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ListComputersServlet.class);	
	
	@Autowired
	private ComputerService computerService;

	

	private int nbComputers = 0;
	private int sizePage = 10;
	private int nbPages = 0;
	private int page = 0;
	private List<Computer> listComputer;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
	
		System.out.println("ListComputersServlet : doGet ... ");
		
		String getpage  = request.getParameter("page");
		String sizePageString  = request.getParameter("nbPages");
		String nameSearsh  = request.getParameter("name");
		String orderBy  = request.getParameter("orderBy");
		
		
		if(getpage!=null && !getpage.isEmpty()) {
			
			page = Integer.parseInt(getpage);
			
			page = page <= 0 ? 1: page;
			page =  (page >= (Math.ceil((nbComputers/(double)sizePage))) ? (int)Math.ceil((nbComputers/(double)sizePage)): page);
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
			LOGGER.info("FindByNameSize : " + listComputer.size());
		}
		else if(orderBy != null && !orderBy.isEmpty()) {
			
			listComputer = computerService.getComputersOrderBy(orderBy);
			System.out.println("nb : " + listComputer.size());
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
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("ListComputersServlet : doPost... ");
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );


	}

	

}