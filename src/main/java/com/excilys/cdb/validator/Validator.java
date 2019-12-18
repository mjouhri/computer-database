package com.excilys.cdb.validator;

import java.time.LocalDateTime;

public class Validator {
	
	public static boolean validateName(String name) {
		if( name != null
				&& !name.isEmpty()
				&& name.length() > 2
				)
			return true;
		return false;
	}

	
	public static boolean validateDate(LocalDateTime introduced, LocalDateTime discontinued) {
		
		if(
				( introduced.getYear() < discontinued.getYear()
				&& introduced.getMonthValue() < discontinued.getMonthValue()
				&& introduced.getDayOfYear() <  introduced.getDayOfYear()

					)
					&& introduced.getYear() > 1975
					&& discontinued.getYear() > 1975) {
			return true;
		}
		
		return false;
	}

}
