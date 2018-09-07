package controllers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.DziekanDAO;
import dao.KandydatDAO;
import entity.Dziekan;
import entity.Kandydat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean

public class CandidateC {

	private Long id = (long) 0;
	private String imie;
	private String nazwisko;
	private String pesel;
	private float srednia;
	private String szkola;

	@EJB
	private KandydatDAO kandydatDAO;

	@EJB
	private DziekanDAO dziekanDAO;

	public String addCandidate() {

		Kandydat kandydat = new Kandydat();

		Dziekan dziekan = dziekanDAO.findOne((long) 0);

		kandydat.setImie(imie);
		kandydat.setNazwisko(nazwisko);
		kandydat.setPesel(pesel);
		kandydat.setSrednia(srednia);
		kandydat.setSzkola(szkola);
		kandydat.setDziekan(dziekan);

		Collection<Kandydat> kandydaci = kandydatDAO.findAll();
		for (Kandydat element : kandydaci) {
			if (element.getPesel().equals(pesel)) {
				return "Ten kandydat juz istnieje";
			}
		}

		kandydatDAO.save(kandydat);

		return "Kandydat dodany";
	}

	public Collection<Kandydat> wyswietlKandydatow() {
		 HttpSession session = (HttpSession)
				 FacesContext.getCurrentInstance().getExternalContext().getSession(false);
				 Dziekan dziekan = (Dziekan) session.getAttribute("dziekan");
		Collection<Kandydat> kandydaci = kandydatDAO.findAll();
		Iterator<Kandydat> it = kandydaci.iterator();
		
		
		
	}
}
