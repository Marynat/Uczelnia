package controllers;

import java.io.Console;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.DziekanDAO;
import dao.KandydatDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.StudentDAO;
import dao.UzytkownikDAO;
import entity.Dziekan;
import entity.Kandydat;
import entity.Przedmiot;
import entity.Semestr;
import entity.Student;
import entity.Uzytkownik;
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
	private SemestrDAO semestrDAO;

	@EJB
	private PrzedmiotDAO przedmiotDAO;

	@EJB
	private UzytkownikDAO uzytkownikDAO;

	@EJB
	private StudentDAO studentDAO;

	@EJB
	private DziekanDAO dziekanDAO;

	private Collection<Kandydat> kandydaci = new LinkedList<>();

	public String addCandidate() {

		Kandydat kandydat = new Kandydat();

		Dziekan dziekan = dziekanDAO.findOne((long) 1);

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
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Dziekan dziekan = (Dziekan) session.getAttribute("dziekan");
		Collection<Kandydat> kandydaci = kandydatDAO.findAll();
		Iterator<Kandydat> it = kandydaci.iterator();
		while (it.hasNext()) {
			Kandydat k = it.next();
			if(k.getDziekan() == null) {
				it.remove();
				break;
			}
			if (!k.getDziekan().getId_dziekan().equals(dziekan.getId_dziekan())) {
				it.remove();
			}

		}

		return kandydaci;

	}


	public void zatwierdzKandydata(long id) {

		Kandydat kandydat;
		kandydat = kandydatDAO.findOne(id);

		Uzytkownik uzytkownik = new Uzytkownik();
		Student student = new Student();
		List<Semestr> semestry = semestrDAO.findAll();
		List<Przedmiot> przedmioty = przedmiotDAO.findAll();

		Random rand = new Random();

		uzytkownik.setLogin(kandydat.getNazwisko() + kandydat.getImie());
		uzytkownik.setHaslo("hasloT123");
		uzytkownik.setRola("STUDENT");
		uzytkownik.setAktywowany(true);
		uzytkownik.setZalogowany(true);
		student.setImie(kandydat.getImie());
		student.setNazwisko(kandydat.getNazwisko());
		student.setNr_albumu(String.valueOf(rand.nextInt(99999) + 10000));
		student.setPesel(kandydat.getPesel());
		student.setUzytkownik(uzytkownik);
		student.setSemestry(semestry);
		student.setPrzedmioty(przedmioty);
		student.setEmail("");
		student.setAdres("");
		studentDAO.save(student);
		kandydat.setDziekan(null);
		kandydatDAO.save(kandydat);

	}
}
