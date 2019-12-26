package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.Main;



public class ListComputersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ListComputersServlet.class);	

	private ComputerService computerService = ComputerService.getInstance();
	private int page = 1;
	private int nbComputers = 0;
	private int sizePage = 10;
	private int nbPages = 0;
	private List<Computer> listComputer;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BasicConfigurator.configure();
	
		System.out.println("ListComputersServlet : doGet ... ");
		
		String getpage  = request.getParameter("page");
		String sizePageString  = request.getParameter("nbPages");
		String nameSearsh  = request.getParameter("name");
		
		System.out.println("page : " + getpage);
		System.out.println("Size page : " + sizePageString);
		System.out.println("name searshed : " + nameSearsh);
		
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

		
//		String getpage  = request.getParameter("page");
//		String sizePageString  = request.getParameter("nbPages");
//		String nameSearsh  = request.getParameter("name");
//		
//		System.out.println("page : " + getpage);
//		System.out.println("Size page : " + sizePageString);
//		System.out.println("name searshed : " + nameSearsh);
//		
//		if(getpage!=null && !getpage.isEmpty()) {
//			
//			page = Integer.parseInt(getpage);
//			
//			page = page <= 0 ? 1: page;
//			page =  (page >= (Math.ceil((nbComputers/(double)sizePage))) ? (int)Math.ceil((nbComputers/(double)sizePage)): page);
//		}
//		
//		if(sizePageString != null && !sizePageString.isEmpty()) {
//			sizePage  = Integer.parseInt(sizePageString);
//		}
//		
//		
//		if(nameSearsh != null && !nameSearsh.isEmpty()) {
//			
//			
//			
//		}
		//if(nameSearsh != null && !nameSearsh.isEmpty())
		
		
		
		data(request);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );


	}

	private void data(HttpServletRequest request) {
		
	}

}