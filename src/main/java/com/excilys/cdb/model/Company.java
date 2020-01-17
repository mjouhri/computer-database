package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@Column(name="id")
	private int idCompany;
	private String nameCompany;
	
	public Company() {  

	}
	
	public Company(int idCompany, String nameCompany) {
		super();
		this.idCompany = idCompany;
		this.nameCompany = nameCompany;
	}

	public int getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}

	public String getNameCompany() {
		return nameCompany;
	}

	public void setNameCompany(String nameCompany) {
		this.nameCompany = nameCompany;
	}

	@Override
	public String toString() {
		return "Company [idCompany=" + idCompany + ", nameCompany=" + nameCompany + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idCompany;
		result = prime * result + ((nameCompany == null) ? 0 : nameCompany.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (idCompany != other.idCompany)
			return false;
		if (nameCompany == null) {
			if (other.nameCompany != null)
				return false;
		} else if (!nameCompany.equals(other.nameCompany))
			return false;
		return true;
	}
	
	private Company(CompanyBuilder builder) {
		this.idCompany = builder.idCompany;
		this.nameCompany = builder.nameCompany;
	}
	
	public static class CompanyBuilder {
		private int idCompany;
		private String nameCompany;

		public CompanyBuilder() {
		}

		public CompanyBuilder idCompany(int idCompany) {
			this.idCompany = idCompany;
			return this;
		}

		public CompanyBuilder nameCompany(String nameCompany) {
			this.nameCompany = nameCompany;
			return this;
		}

		public Company build() {
			return new Company(this);
		}
	}
	
	
	
	

}
