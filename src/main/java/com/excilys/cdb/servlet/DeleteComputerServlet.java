//package com.excilys.cdb.servlet;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.excilys.cdb.service.ComputerService;
//
//@WebServlet(name = "DeleteComputerServlet", urlPatterns = {"/deletecomputer"})
//public class DeleteComputerServlet extends HttpServlet{
//	
//	private static final long serialVersionUID = 1L;
//	@Autowired
//	private ComputerService computerService ;
//	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputerServlet.class);	
//
//
//	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		LOGGER.info("DeleteComputerServlet : doGet ... ");
//		
//		int idComputer  = Integer.parseInt(request.getParameter("idComputer"));
//		
//		computerService.deleteComputer(idComputer);
//		
//		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
//	
//	}
//	
//
//}
