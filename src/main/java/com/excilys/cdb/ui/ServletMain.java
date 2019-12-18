package com.excilys.cdb.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;



public class ServletMain extends HttpServlet {

	private ComputerService computerService = ComputerService.getInstance();
	int page = 1;
	int nbComputers = 0;
	int sizePage = 20;
	int nbPages = 0;
	
	List<Computer> listComputer ;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		listComputer = computerService.getPage(page, sizePage);
		nbComputers = computerService.getNbComputers();
		nbPages  = (int) Math.ceil((nbComputers/(double)sizePage));
		request.setAttribute("nbComputers", nbComputers);
		request.setAttribute("computers", listComputer);
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("page", page);

		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String getpage  = request.getParameter("page"); 
		page = Integer.parseInt(getpage);
		page = page <= 0 ? 1: page;
		page =  (page >= (Math.ceil((nbComputers/(double)sizePage))) ? (int)Math.ceil((nbComputers/(double)sizePage)): page);
		
		
		listComputer = computerService.getPage(page, sizePage);
		nbComputers = computerService.getNbComputers();
		nbPages  = (int) Math.ceil((nbComputers/(double)sizePage));
		request.setAttribute("nbComputers", nbComputers);
		request.setAttribute("computers", listComputer);
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("page", page);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );


	}

}