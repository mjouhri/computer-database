package com.excilys.cdb.dto;

import java.time.LocalDateTime;

import com.excilys.cdb.mapper.Mapper;
import com.excilys.cdb.model.Computer.ComputerBuilder;

public class Computer {
	
	private int id;
	private String name;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private int idCompany;
	private String nameCompany;
	
	public Computer() {
		super();
	}
	
	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.idCompany = builder.idCompany;
		this.nameCompany = builder.nameCompany;
	}

	public Computer(String name, LocalDateTime introduced, LocalDateTime discontinued, int idCompany,
			String nameCompany) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.idCompany = idCompany;
		this.nameCompany = nameCompany;
	}
	
	public static class ComputerBuilder {
		private int id;
		private String name;
		private LocalDateTime introduced;
		private LocalDateTime discontinued;
		private int idCompany;
		private String nameCompany;
		
		public ComputerBuilder() {
		}
		
		public ComputerBuilder setId(int id) {
			this.id = id;
			return this;
		}
		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}
		public ComputerBuilder setIntroduced(LocalDateTime introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public ComputerBuilder setIntroduced(String introduced) {
			
			Mapper.StringToLocalDateTime(introduced);
		
			return this;
		}


		public ComputerBuilder setDiscontinued(LocalDateTime discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerBuilder setDiscontinued(String discontinued) {
			
			Mapper.StringToLocalDateTime(discontinued);
			
			return this;
		} 
		
		public ComputerBuilder setIdCompany(int idCompany) {
			this.idCompany = idCompany;
			return this;
		}

		public ComputerBuilder setNameCompany(String nameCompany) {
			this.nameCompany = nameCompany;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}
		
		
	}
	
 

}


