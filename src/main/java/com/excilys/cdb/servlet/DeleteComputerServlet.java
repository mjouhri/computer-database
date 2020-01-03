//package com.excilys.cdb.servlet;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.excilys.cdb.service.ComputerService;
//
//public class DeleteComputerServlet extends HttpServlet{
//	
//	private static final long serialVersionUID = 1L;
//	private ComputerService computerService = ComputerService.getInstance();
//	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteComputerServlet.class);	
//
//
//	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		LOGGER.info("DeleteComputerServlet : doGet ... ");
//
//		
//		int idComputer  = Integer.parseInt(request.getParameter("idComputer"));
//		
//		
//		computerService.deleteComputer(idComputer);
//		
////	
//		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
//	
//	}
//	
//
//}
