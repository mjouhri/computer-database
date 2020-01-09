//package com.excilys.cdb.servlet;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.excilys.cdb.model.Company;
//import com.excilys.cdb.model.Computer;
//import com.excilys.cdb.service.CompanyService;
//import com.excilys.cdb.service.ComputerService;
//import com.excilys.cdb.validator.Validator;
//
//
//@WebServlet(name = "EditComputerServlet", urlPatterns = {"/editComputer"})
//public class EditComputerServlet extends HttpServlet{
//	private static final long serialVersionUID = 1L;
//	private static final Logger LOGGER = LoggerFactory.getLogger(EditComputerServlet.class);	
//	@Autowired
//	private CompanyService companyService;
//	@Autowired
//	private ComputerService computerService;
//	private List<Company> listCompany;
//	Computer computer ;
//
//	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		LOGGER.info("EditComputerServlet : doGet ... ");
//		
//		int idComputer  = Integer.parseInt(request.getParameter("companyId"));
//		
//		computer = computerService.getComputerById(idComputer);
//		listCompany = companyService.getListCompany();
//		
//		request.setAttribute("name", computer.getName());
//		request.setAttribute("introduced", computer.getIntroduced());
//		request.setAttribute("discontinued", computer.getDiscontinued());
//		request.setAttribute("companys", listCompany);
//		
//
//		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/editComputer.jsp" ).forward( request, response );
//	
//	}
//	
//	
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		LOGGER.info("EditComputerServlet : doPOST .... ");
//
//		String computerName   = request.getParameter("computerName"); 
//		String introduced   = request.getParameter("introduced");
//		String discontinued   = request.getParameter("discontinued"); 
//		int companyId  = Integer.parseInt(request.getParameter("companyId"));
//		
//		if(Validator.ValidateAddComputer(computerName, introduced, discontinued)) {
//			computerService.updateComputer(
//					
//					new Computer.ComputerBuilder()
//									.setId(computer.getId())
//									.setName(computerName)
//									.setIntroduced(introduced)
//									.setDiscontinued(discontinued)
//									.setCompany(new Company.CompanyBuilder()
//													.idCompany(companyId)
//													.nameCompany(null)
//													.build())
//									.build()
//								);
//		}
//		
//		listCompany = companyService.getListCompany();
//		request.setAttribute("listCompany", listCompany);
//		
//		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/dashboard.jsp" ).forward( request, response );
//
//	}
//}
