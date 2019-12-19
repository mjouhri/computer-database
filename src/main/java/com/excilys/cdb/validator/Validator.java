package com.excilys.cdb.validator;

import java.time.LocalDateTime;

public class Validator {
	
	public static boolean validateName(String name) {
		return name != null && !name.isEmpty() && name.length() > 2 ? true : false;
	}
	
	public static boolean validateDate(LocalDateTime introduced, LocalDateTime discontinued) {
		
		if(isvalidDate(introduced) && !isvalidDate(discontinued)) return true;
		else if(isvalidDate(introduced) 
				&& isvalidDate(discontinued)
				&& introduced.isBefore(discontinued)) return true;
		else return false;
	
	}
		
	public static boolean isvalidDate(LocalDateTime date) {
			return date != null && date.getYear() > 1970 ? true : false;
	}
			
}
