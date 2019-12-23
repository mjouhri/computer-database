package com.excilys.cdb.validator;

import java.time.LocalDateTime;

import com.excilys.cdb.mapper.Mapper;

public class Validator { // retourner des exceptions
	
	private static boolean validateName(String name) {
		return name != null && !name.isEmpty() && name.length() > 2 ? true : false;
	}
	
	public static boolean ValidateAddComputer( String name, String introduced, String discontinued) {
		if (validateDate(Mapper.StringToLocalDateTime(introduced),
				Mapper.StringToLocalDateTime(discontinued)) 
				&& validateName(name)) return true;
		else return false;
	}
	
	private static boolean validateDate(LocalDateTime introduced, LocalDateTime discontinued) {
		
		if(isvalidDate(introduced) && !isvalidDate(discontinued)) return true;
		else if(isvalidDate(introduced)
				&& isvalidDate(discontinued)
				&& introduced.isBefore(discontinued)) return true;
		else return false;
	
	}
		
	private static boolean isvalidDate(LocalDateTime date) {
			return date != null && date.getYear() > 1970 ? true : false;
	}
			
}