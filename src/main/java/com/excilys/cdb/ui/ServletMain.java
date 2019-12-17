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
	List<Computer> listComputer ;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		listComputer = computerService.getPage(page, 20);
		request.setAttribute("computers", listComputer);
		System.out.println("goGet");

		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doPost");
		
		int nbPagae=1;
		String page  = request.getParameter("page");
		
		if(page.equals("next"))  nbPagae++;
		else if(page.equals("back"))  nbPagae--;
		else {
			nbPagae = Integer.parseInt(page);
		}

		 
		listComputer = computerService.getPage(nbPagae, 20);
		request.setAttribute("computers", listComputer);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );


	}

}