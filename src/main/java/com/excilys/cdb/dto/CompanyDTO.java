package com.excilys.cdb.dto;

public class CompanyDTO {
	
	private int id;
	private String name;
	
	public CompanyDTO() {
		super();
	}
	
	private CompanyDTO(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public static class CompanyBuilder {
		private int id;
		private String name;
	
		
		public CompanyBuilder() {
		}
		
		public CompanyBuilder setId(int id) {
			this.id = id;
			return this;
		}
		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public CompanyDTO build() {
			return new CompanyDTO(this);
		}
		
		
	}
	
	

}
