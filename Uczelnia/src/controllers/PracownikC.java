package controllers;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.PlatnosciDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.StudentDAO;
import dao.WnioskiDAO;
import entity.Pracownik_dydaktyczny;
import entity.Przedmiot;
import entity.Student;
import lombok.Data;

@RequestScoped
@Named
@Data
@LocalBean

public class PracownikC {

	@EJB
	private StudentDAO studentDAO;

	@EJB
	private PrzedmiotDAO przedmiotDAO;

	@EJB
	private SemestrDAO semestrDAO;
	
	@EJB
	private WnioskiDAO wnioskiDAO;
	
	public Collection<Przedmiot> pokazPrzedmioty() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pracownik_dydaktyczny pracownik = (Pracownik_dydaktyczny) session.getAttribute("pracownik_dydaktyczny");

		Collection<Przedmiot> przedmioty = przedmiotDAO.findAll();

		Iterator<Przedmiot> iter = przedmioty.iterator();

		while (iter.hasNext()) {
			Przedmiot p = iter.next();
			if (!p.getPracownik().getId_pracownik_dydaktyczny().equals(pracownik.getId_pracownik_dydaktyczny())) {
				iter.remove();
			}
		}

		return przedmioty;
	}


}
