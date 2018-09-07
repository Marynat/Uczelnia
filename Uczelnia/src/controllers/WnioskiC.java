package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.StudentDAO;
import dao.AdministratorDAO;
import dao.DziekanDAO;
import dao.PlatnosciDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.PracownikDydaktycznyDAO;
import dao.UzytkownikDAO;
import dao.WnioskiDAO;
import entity.Student;
import entity.Administrator;
import entity.Dziekan;
import entity.Kandydat;
import entity.Platnosci;
import entity.Pracownik_dydaktyczny;
import entity.Przedmiot;
import entity.Semestr;
import entity.Uzytkownik;
import entity.Wnioski;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class WnioskiC {

	@EJB
	private StudentDAO studentDAO;

	@EJB
	private DziekanDAO dziekanDAO;
	
	@EJB
	private WnioskiDAO wnioskiDAO;
	
	@EJB
	private PlatnosciDAO platnosciDAO;

	private String imie;
	private String nazwisko;
	private String pesel;
	private float kwota;
	
	private String tytul = "";
	
	private String wiadomosc = "";

	private String message = "";

	public boolean sprawdzStatus(String status) {

		if (status.equals("ROZPATRZONE")) {
			return true;
			}

		return false;
	}

	public boolean sprawdzZgoda(boolean zgoda) {
		if (zgoda == true) {
			return true;
		}

		return false;
	}

	public void wyslijS() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Student student = (Student) session.getAttribute("student");
		Collection<Dziekan> dziekani = dziekanDAO.findAll();
		Dziekan dziekan = null;
		int i=1;
		Random rand = new Random();

		for(Iterator<Dziekan> iter = dziekani.iterator(); iter.hasNext(); dziekan = iter.next()) {
			if(i>rand.nextInt(dziekani.size())+1)
				break;
			i++;
		}
		
		
		Wnioski wniosek = new Wnioski();
		
		wniosek.setTytul(tytul);
		wniosek.setWiadomosc(wiadomosc);
		wniosek.setDziekan(dziekan);
		wniosek.setStudent(student);
		wniosek.setPracownik(null);
		wniosek.setStatus("NIEROZPATRZONE");
		
		wnioskiDAO.save(wniosek);
		
	}

	public void wyslijP() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Pracownik_dydaktyczny pracownik = (Pracownik_dydaktyczny) session.getAttribute("pracownik_dydaktyczny");
		Collection<Dziekan> dziekani = dziekanDAO.findAll();
		Dziekan dziekan = null;
		int i=1;
		Random rand = new Random();

		for(Iterator<Dziekan> iter = dziekani.iterator(); iter.hasNext(); dziekan = iter.next()) {
			if(i>rand.nextInt(dziekani.size())+1)
				break;
			i++;
		}
		
		
		Wnioski wniosek = new Wnioski();
		
		wniosek.setTytul(tytul);
		wniosek.setWiadomosc(wiadomosc);
		wniosek.setDziekan(dziekan);
		wniosek.setStudent(null);
		wniosek.setPracownik(pracownik);
		wniosek.setStatus("NIEROZPATRZONE");
		
		wnioskiDAO.save(wniosek);
	}
	
public String addPlatnosc() {
	
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

	Administrator admin = (Administrator) session.getAttribute("administrator");
		
		Platnosci platnosci = new Platnosci();
		
		Collection<Student> studenci = studentDAO.findAll();
		Iterator<Student> it = studenci.iterator();
		Student student = new Student();
		
		while(it.hasNext()) {
			Student s = it.next();
			if(s.getPesel().equals(pesel)) {
				student = s;
				}
		}
		if(student == null) {
			return "z³y student";
		}
		
		platnosci.setTytul(tytul);
		platnosci.setStatus("NIEZAPLACONE");
		platnosci.setCena(kwota);
		platnosci.setAdmin(admin);
		platnosci.setStudent(student);
		
		
		platnosciDAO.save(platnosci);
		
		return "platnosc dodana";
	}
}
