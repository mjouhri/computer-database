package com.excilys.cdb.validator;

import java.time.LocalDateTime;

import com.excilys.cdb.exeception.DateFormatException;
import com.excilys.cdb.exeception.DateIntervaleExecption;
import com.excilys.cdb.exeception.NameFormatException;

public class Validator { 	
	
	public static void validationComputer(String name, LocalDateTime introduced, 
			LocalDateTime discontinued) throws DateIntervaleExecption, DateFormatException, NameFormatException{

			validationName(name);
			validationDate(introduced, discontinued);
			

	}
	
	private static void validationDate(LocalDateTime introduced, LocalDateTime discontinued) throws DateIntervaleExecption, DateFormatException {
		
			if(introduced == null) {
				throw new DateFormatException("Date introduced must be initialized");
				}
			else if(discontinued == null) {
				throw new DateFormatException("Date discontinued must be initialized");
				}
			else if(introduced.getYear() < 1970 && discontinued.getYear() < 1970) {
				throw new DateFormatException("year must be after 1970");
				}
			else if(!introduced.isBefore(discontinued)) {
				throw new DateIntervaleExecption("Date introduced'" +
						introduced.toString() + "' must be before date discontinued '" +  
						discontinued.toString() + "'");
			}	
	}
	
	
	private static void validationName(String name) throws NameFormatException{
			if(name != null && !name.isEmpty() && name.length() > 2) throw new NameFormatException("Name must be more than 2 characters");	
	}
			
}