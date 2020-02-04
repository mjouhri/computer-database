package com.excilys.cdb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.service.ComputerService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/computers")
public class ComputerControllerRest {
	
	
	private ComputerService computerService;
	
		
	public ComputerControllerRest(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@CrossOrigin
	@GetMapping
	@ApiOperation(value = "${swagger.computers}", notes = "${swagger.computers.desc}")
	public List<ComputerDTO> getAll() {	
		return computerService.getAll();
	}
	
	@CrossOrigin
	@PostMapping
	public boolean create(ComputerDTO computer) {	
		return computerService.newComputer( Mapper.computerDAOToComputer(computer) );
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public boolean delete(int id) {	
		return computerService.deleteComputer(id);
	}
	
	@CrossOrigin
	@PutMapping
	public boolean edit(ComputerDTO computer) {	
		return computerService.updateComputer( Mapper.computerDAOToComputer(computer) );
	}
	
	
}
