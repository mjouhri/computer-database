package com.excilys.cdb.exeception;

public class DateFormatException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public DateFormatException (String errorMessage) {
		super(errorMessage);
	}

}
