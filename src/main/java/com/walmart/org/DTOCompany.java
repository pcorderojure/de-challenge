package com.walmart.org;

public class DTOCompany 
{	
	private int id_company;
	private String name_company;
	
	public DTOCompany (int id , String compania) 
	{
		this.id_company = id;
		this.name_company = compania;
	}	
	
	public int getId_company() {
		return id_company;
	}
	public void setId_company(int id_company) {
		this.id_company = id_company;
	}
	public String getName_company() {
		return name_company;
	}
	public void setName_company(String name_company) {
		this.name_company = name_company;
	}

}
