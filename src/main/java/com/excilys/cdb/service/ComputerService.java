package com.excilys.cdb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

@Service
public class ComputerService {
	
	private ComputerDAO computerDAO;

//	private static ComputerService INSTANCE = null;

//	private ComputerService() {
//		this.computerDAO = ComputerDAO.getInstance();
//		
//	}
//	
//	public static ComputerService getInstance() {
//		if (INSTANCE == null) INSTANCE = new ComputerService();
//		return INSTANCE;
//	}

	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}	
	 
	public List<Computer> getListComputer() {
		return computerDAO.getListComputer();
	}
	
	public Computer getComputerById(int id) {
		return computerDAO.getComputerById(id).orElse(null);
	}
	
	public boolean newComputer(Computer computer) {
		return computerDAO.newComputer(computer);
	}
	
	public void updateComputer(Computer computer) {
		computerDAO.updateComputer(computer);
	}
	
	public void deleteComputer(int id) {
		computerDAO.deleteComputer(id);
	}
	
	public List<Computer>  getPage(int page, int length) {
		return computerDAO.getPage(page, length);
	} 
	
	public int getNbComputers() {
		return computerDAO.getNbComputers();
	}
	
	public List<Computer>  getComputersByName(String name) {
		return computerDAO.getComputersByName(name);
	}
	
	public List<Computer> getComputersOrderBy(String columnName){
		
		return computerDAO.getComputersOrderBy(columnName);
	}

}
