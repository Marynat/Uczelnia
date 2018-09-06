package controllers;

import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Data;

@RequestScoped
@Named
@Data
@LocalBean
public class ComponentC{

	public String getMyAcc() {
		return "myAcc.xhtml";
	} 
	
	public String getHead() {
		return "head.xhtml";
	}
	
	public String getAdminMenu() {
		return "adminMenu.xhtml";
	}
	
	public String getPracownikMenu() {
		return "pracownikMenu.xhtml";
	}
	
	public String getStudentMenu() {
		return "studentMenu.xhtml";
	}
	
	public String getDziekanMenu() {
		return "dziekanMenu.xhtml";
	}
	
	public String getAcc() {
		return "accBanner.xhtml";
	}
}
