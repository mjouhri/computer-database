package com.excilys.cdb.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping(value = "/computers")
public class ComputerControllerRest {
	
	
	private ComputerService computerService;
	
		
	public ComputerControllerRest(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@CrossOrigin
	@GetMapping
	public List<ComputerDTO> getAll() {
		System.out.println("ComputerControllerRest");
		
		return computerService.getAll();
	}
	
}
