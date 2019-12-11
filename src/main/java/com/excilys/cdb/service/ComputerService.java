package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerService {
	private ComputerDAO computerDAO;
	
	public ComputerService() {
		this.computerDAO = new ComputerDAO();
		
	}
	
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}	
	 
	public List<Computer> getListComputer() {
		return computerDAO.getListComputer();
	}
	
	public Computer getComputerById(int id) {
		return computerDAO.getComputerById(id).orElse(new Computer());
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
	
	public void close() {
		computerDAO.close();
	}
	
	

}
