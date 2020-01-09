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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.Validator;


@WebServlet(name = "AddComputerServlet", urlPatterns = {"/addcomputer"})
public class AddComputerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService ;
	private List<Company> listCompany;
	private static final Logger LOGGER = LoggerFactory.getLogger(AddComputerServlet.class);	
	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.info("AddComputerServlet : doGet ... ");
		
		listCompany = companyService.getListCompany();
		
		request.setAttribute("listCompany", listCompany);
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOGGER.info("AddComputerServlet : doPost... ");
		
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
		
		//// gestion des erreurs avec les exceptions
		
		listCompany = companyService.getListCompany();
		request.setAttribute("listCompany", listCompany);
				
		
		/// afficher le message d'erreur au cas ou et rederection vers listPage
		
		/// setQttribute error et l'afficher dans jsp
		this.getServletContext().getRequestDispatcher( "/WEB-INF/views/addComputer.jsp" ).forward( request, response );

	}

}
