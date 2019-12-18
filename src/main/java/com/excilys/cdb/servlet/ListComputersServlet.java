package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;



public class ListComputersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private int page = 1;
	private int nbComputers = 0;
	private int sizePage = 20;
	private int nbPages = 0;
	private List<Computer> listComputer;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("ListComputersServlet : doGet ... ");

		data(request);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("ListComputersServlet : doPost... ");

		
		String getpage  = request.getParameter("page"); 
		page = Integer.parseInt(getpage);
		page = page <= 0 ? 1: page;
		page =  (page >= (Math.ceil((nbComputers/(double)sizePage))) ? (int)Math.ceil((nbComputers/(double)sizePage)): page);
		
		
		data(request);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );


	}

	private void data(HttpServletRequest request) {
		listComputer = computerService.getPage(page, sizePage);
		nbComputers = computerService.getNbComputers();
		nbPages  = (int) Math.ceil((nbComputers/(double)sizePage));
		request.setAttribute("nbComputers", nbComputers);
		request.setAttribute("computers", listComputer);
		request.setAttribute("nbPages", nbPages);
		request.setAttribute("page", page);
	}

}