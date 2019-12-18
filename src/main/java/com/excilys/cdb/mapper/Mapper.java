package com.excilys.cdb.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {
	
	public static LocalDateTime StringToLocalDateTime(String introduced) {
		introduced = introduced + " 00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(introduced, formatter);
	}

}
