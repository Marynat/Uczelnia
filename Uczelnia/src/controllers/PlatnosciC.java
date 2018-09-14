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
	
	Platnosci plat;
	
	float cena = (float) 10.0;
	
	public boolean sprawdzStatus(String status) {
		
		if(status.equals("ZAPLACONE"))
			return true;
		
		return false;
	}
	
	public void przenies(long id) {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Platnosci platnosc = null;
		platnosc = platnosciDAO.findOne(id);
		
		
		session.setAttribute("platnosc", platnosc);
	}
	
	public float zwrocP() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		plat = (Platnosci) session.getAttribute("platnosc");
		cena = plat.getCena();
		return cena;
	}
	
	public void updateP() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		plat = (Platnosci) session.getAttribute("platnosc");
		plat.setStatus("ZAPLACONE");
		platnosciDAO.save(plat);
	}
	public void updatePz() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		plat = (Platnosci) session.getAttribute("platnosc");
		plat.setStatus("NIEZAPLACONE");
		platnosciDAO.save(plat);
	}

}
