package controllers;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.DziekanDAO;
import dao.KandydatDAO;
import dao.PracownikDydaktycznyDAO;
import dao.StudentDAO;
import dao.UczelniaDAO;
import dao.UzytkownikDAO;
import entity.Administrator;
import entity.Dziekan;
import entity.Kandydat;
import entity.Pracownik_dydaktyczny;
import entity.Student;
import entity.Uczelnia;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class AutorizationC {

	@Getter
	@Setter
	private Autoryzacja autoryzacja = new Autoryzacja();

	@Getter
	@Setter
	private String errorMessage = "";

	@Getter
	@Setter
	private String errorMessageU = "";

	@Getter
	@Setter
	private String url = "logowanie.xhtml";

	@Getter
	@Setter
	private Uzytkownik nowyUzytkownik = new Uzytkownik();

	@EJB
	private UzytkownikDAO uzytkownikDAO;

	@EJB
	private UczelniaDAO uczelniaDAO;

	@EJB
	private DziekanDAO dziekanDAO;
	
	@EJB
	private StudentDAO studentDAO;

	public boolean czyZalogowany() {
		return getUzytkownik() != null;
	}

	public Uzytkownik getUzytkownik() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		return (Uzytkownik) session.getAttribute("uzytkownik");
	}

	public String addNewUser() {
		nowyUzytkownik.setZalogowany(true);
		uzytkownikDAO.save(nowyUzytkownik);
		return "Dodano";
	}

	@Data
	public class Autoryzacja {
		private String login ="";
		private String haslo ="";
		private String imie="";
		private String nazwisko = "";
		private String email = "";
	}

	public String ktoZalogowany(Uzytkownik uzytkownik) {
		if (uzytkownik == null)
			return "NIEZALOGOWANY";

		if (uzytkownik.getRola().equals("ADMINISTRATOR"))
			return "ADMINISTRATOR";

		if (uzytkownik.getRola().equals("PRACOWNIK"))
			return "PRACOWNIK";

		if (uzytkownik.getRola().equals("CZYTELNIK"))
			return "CZYTELNIK";

		return "BLAD";
	}

	public String zaloguj() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (autoryzacja.getLogin().isEmpty() || autoryzacja.getHaslo().isEmpty()) {
			errorMessage = "Wypełnij wszystkie pola";
			return "moje_konto";
		}
		Uzytkownik uzytkownik;
		try {
			uzytkownik = uzytkownikDAO.findByQuery(Uzytkownik.builder().login(autoryzacja.getLogin())
					.haslo(autoryzacja.getHaslo()).rola("").zalogowany(true).aktywowany(true).build()).iterator()
					.next();
		} catch (NoSuchElementException e) {
			errorMessage = "Bledny login lub haslo";
			return "moje_konto";
		}
		if (uzytkownik == null) {
			errorMessage = "Bledny login lub haslo";
			return "moje_konto";
		} else {
			if (uzytkownik.getRola().equals("STUDENT")) {
				url = "profil_studenta.xhtml";
				if (!uzytkownik.isAktywowany()) {
					errorMessage = "User o login " + uzytkownik.getLogin() + " jest nieaktywny";
					return "moje_konto";
				}

				session.setAttribute("uzytkownik", uzytkownik);
				return "profil_studenta";
			}
			if (uzytkownik.getRola().equals("PRACOWNIK")) {
				url = "profil_pracownika.xhtml";
				session.setAttribute("uzytkownik", uzytkownik);
				return "profil_pracownika";
			}
			if (uzytkownik.getRola().equals("ADMINISTRATOR")) {
				url = "profil_administratora.xhtml";
				session.setAttribute("uzytkownik", uzytkownik);
				return "profil_administratora";
			}
			if (uzytkownik.getRola().equals("DZIEKAN")) {
				url = "profil_dziekana.xhtml";
				session.setAttribute("uzytkownik", uzytkownik);
				return "profil_dziekana";
			}
		}

		return "moje_konto";
	}
	
	@Data
	class GoogleUser {
		
		private String Eea;
		private String	Paa;
		private String	U3;
		private String	ig;
		private String	ofa;
		private String	wea;
		
	}
	
	public void saveGoogleUser() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		
		GoogleUser googleUser = (GoogleUser) session.getAttribute("googleUser");
		
		autoryzacja.setEmail(googleUser.getU3());
		
		Uzytkownik uzytkownik = null;
		Student student;
		
		try {
			student = studentDAO.findByQuery(Student.builder().email(autoryzacja.email).build()).stream().findFirst().get();
			uzytkownik = uzytkownikDAO.findOne(student.getUzytkownik().getId_uzytkownik());
		} catch (NoSuchElementException e) {
			errorMessage = "Bledny login lub haslo";
		}
		
		if (uzytkownik == null) {
			errorMessage = "Bledny login lub haslo";
		} else {
			if (uzytkownik.getRola().equals("STUDENT")) {
				url = "profil_studenta.xhtml";
				if (!uzytkownik.isAktywowany()) {
					errorMessage = "User o login " + uzytkownik.getLogin() + " jest nieaktywny";
				}

				session.setAttribute("uzytkownik", uzytkownik);
			}
			if (uzytkownik.getRola().equals("PRACOWNIK")) {
				url = "profil_pracownika.xhtml";
				session.setAttribute("uzytkownik", uzytkownik);
			}
			if (uzytkownik.getRola().equals("ADMINISTRATOR")) {
				url = "profil_administratora.xhtml";
				session.setAttribute("uzytkownik", uzytkownik);
			}
			if (uzytkownik.getRola().equals("DZIEKAN")) {
				url = "profil_dziekana.xhtml";
				session.setAttribute("uzytkownik", uzytkownik);
			}
		}

		
	}

	public String sprawdzCzyZalogowany() {
		String login;
		if (czyZalogowany() == true) {

			login = getUzytkownik().getLogin();
			return login;
		}
		login = "Moje konto";
		return login;
	}

	public String getWyloguj() {
		return "wyloguj.xhtml";
	}

	public String wyloguj() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		// session.removeAttribute("uzytkownik");
		//
		//
		// session.removeAttribute("student");
		// session.removeAttribute("dziekan");
		// session.removeAttribute("pracownik_dydaktyczny");
		// session.removeAttribute("administrator");

		session.invalidate();

		url = "moje_konto.xhtml";
		errorMessage = "";
		return "moje_konto.xhtml";
	}

	public String sprawdzKtoZalogowany() {
		Uzytkownik uzytkownik = getUzytkownik();
		if (uzytkownik == null)
			return "NIEZALOGOWANY";

		if (uzytkownik.getRola().equals("ADMINISTRATOR"))
			return "ADMINISTRATOR";

		if (uzytkownik.getRola().equals("STUDENT"))
			return "STUDENT";

		if (uzytkownik.getRola().equals("DZIEKAN"))
			return "DZIEKAN";

		if (uzytkownik.getRola().equals("PRACOWNIK"))
			return "PRACOWNIK";

		return "BLAD";
	}

	public boolean sprawdzRekrutacje() {

		Uczelnia uczelnia = null;
		try {
			uczelnia = uczelniaDAO.findOne((long) 1);

		} catch (NoSuchElementException e) {
			errorMessageU = "Nie moge znalezc uczelni w bazie danych";
			return false;
		}

		if (uczelnia == null) {
			errorMessageU = "Nie moge znalezc uczelni w bazie danych";
			return false;
		}

		if (uczelnia.isRekrutacja()) {
			return true;
		} else
			return false;
	}

	public void checkSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
	
	public String zmienRekrutacje() {
		Uczelnia uczelnia = uczelniaDAO.findOne((long) 1);
			uczelnia.setRekrutacja(!uczelnia.isRekrutacja());
			uczelniaDAO.save(uczelnia);
			return "Rekrutacja została zmieniona";
	}

}
