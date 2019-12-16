package com.excilys.cdb.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;


//@WebServlet(name = "Dashboard", urlPatterns = "/dashboard")
public class ServletMain extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		List<Computer> listComputer = computerService.getListComputer();
		request.setAttribute("listComputer", listComputer);


		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
	}

}