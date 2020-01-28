package com.excilys.cdb.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;

public class Mapper {
	
	public static LocalDateTime StringToLocalDateTime(String introduced) {
		introduced = introduced + " 00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(introduced, formatter);
	}
	
	public static ComputerDTO computerToComputerDTO(Computer computer) {
		ComputerDTO c = new ComputerDTO.ComputerBuilder()
				.setId(computer.getId())
				.setName(computer.getName())
				.setIntroduced(computer.getIntroduced())
				.setDiscontinued(computer.getDiscontinued())
				.setIdCompany(computer.getCompany().getIdCompany())
				.setName(computer.getCompany().getNameCompany())
				.build();
		return c;
	}
	
	public static List<ComputerDTO> listComputerToComptersDTO(List<Computer> list){
		List<ComputerDTO> listDTO = new ArrayList<ComputerDTO> ();
		
		for (Computer computer : list) {
			listDTO.add(computerToComputerDTO(computer));
		}
		
		return listDTO;
		
	}

}
