package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;


@WebServlet(name = "ListComputersServlet", urlPatterns = {"/"})
public class ListComputersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(ListComputersServlet.class);	
	private int nbComputers = 0;
	private int sizePage = 10;
	private int nbPages = 0;
	private int page = 0;
	private List<Computer> listComputer;
	
	@Autowired
	private ComputerService computerService;
	
//	public ListComputersServlet(ComputerService computerService) {
//		this.computerService = computerService;
//	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		LOG.info("ListComputersServlet:doGet ...");
		
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
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );

	}

	

}