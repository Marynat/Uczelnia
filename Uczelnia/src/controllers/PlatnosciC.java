package controllers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.StudentDAO;
import dao.AdministratorDAO;
import dao.PlatnosciDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.PracownikDydaktycznyDAO;
import dao.UzytkownikDAO;
import entity.Student;
import entity.Administrator;
import entity.Dziekan;
import entity.Platnosci;
import entity.Pracownik_dydaktyczny;
import entity.Przedmiot;
import entity.Semestr;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class PlatnosciC {
	
	@EJB
	private PlatnosciDAO platnosciDAO;
	
	@EJB
	private StudentDAO studentDAO;
	
	public boolean sprawdzStatus(String status) {
		
		if(status.equals("ZAPLACONE"))
			return true;
		
		return false;
	}

}
