package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.Validator;

public class AddComputerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private ComputerService computerService = ComputerService.getInstance();
	private CompanyService companyService = CompanyService.getInstance();
	private List<Company> listCompany;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("AddComputerServlet : doGet ... ");
		
		listCompany = companyService.getListCompany();
		
		request.setAttribute("listCompany", listCompany);
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("AddComputerServlet : doPost... ");
		
		String computerName   = request.getParameter("computerName"); 
		String introduced   = request.getParameter("introduced");
		String discontinued   = request.getParameter("discontinued"); 
		int companyId  = Integer.parseInt(request.getParameter("companyId"));
		
		if(Validator.ValidateAddComputer(computerName, introduced, discontinued)) {
			computerService.newComputer(
					
					new Computer.ComputerBuilder()
									.setName(computerName)
									.setIntroduced(introduced)
									.setDiscontinued(discontinued)
									.setCompany(new Company.CompanyBuilder()
													.idCompany(companyId)
													.nameCompany(null)
													.build())
									.build()
								);
		}
		
		listCompany = companyService.getListCompany();
		request.setAttribute("listCompany", listCompany);
				
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );

	}

}
