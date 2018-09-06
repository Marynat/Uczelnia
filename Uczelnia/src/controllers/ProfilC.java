package controllers;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.StudentDAO;
import dao.AdministratorDAO;
import dao.DziekanDAO;
import dao.PracownikDydaktycznyDAO;
import dao.UzytkownikDAO;
import entity.Student;
import entity.Administrator;
import entity.Dziekan;
import entity.Pracownik_dydaktyczny;
import entity.Uzytkownik;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RequestScoped
@Named
@Data
@LocalBean
public class ProfilC {

	@EJB
	private StudentDAO studentDAO;

	@EJB
	private PracownikDydaktycznyDAO pracownikDydaktycznyDAO;

	@EJB
	private UzytkownikDAO uzytkownikDAO;

	@EJB
	private AdministratorDAO administratorDAO;

	@EJB
	private DziekanDAO dziekanDAO;

	private AutorizationC autoController = new AutorizationC();
	
	@Getter
	@Setter
	private String errorMessageLogin = "";
	
	@Getter
	@Setter
	private String errorMessageEmail = "";
	
	@Getter
	@Setter
	private String errorMessage = "";
	
	private Long id = (long) 0;
	private String imie = "";
	private String nazwisko = "";
	private String pesel = "";
	private String email = "";
	private String adres = "";
	private String login = "";
	private String haslo = "";
	private Long kara = (long) 0;
	private int aktWypozyczen = 0;
	private int sumWypozyczen = 0;
	private String nr_albumu = "";
	private int zarejestrowaniUzytkownicy = 0;
	

	public String ustawProfil() {

		 HttpSession session = (HttpSession)
		 FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		 Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");

		if (uzytkownik == null) {
			return "Niezalogowany";
		}

		else if (uzytkownik.getRola().equals("NIEZALOGOWANY")) {
			return "Niezalogowany";
		}

		else if (uzytkownik.getRola().equals( "PRACOWNIK")) {
			imie = ustawProfilPracownika();
		}

		else if (uzytkownik.getRola().equals("STUDENT")) {
			imie = ustawProfilStudenta();
		}

		else if (uzytkownik.getRola().equals("DZIEKAN")) {
			imie = ustawProfilDziekana();
		}

		else if (uzytkownik.getRola().equals("ADMINISTRATOR")) {
			imie = ustawProfilAdministratora();
		}
		return imie;
	}

	public String ustawProfilStudenta() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");

		Collection<Student> studenci = studentDAO.findAll();

		if (uzytkownik == null)
			return String.valueOf(studenci.size());


		for (Student element : studenci) {
			if ((element.getUzytkownik().getId_uzytkownik().equals(uzytkownik.getId_uzytkownik()))) {
				id = element.getId_student();
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				adres = element.getAdres();
				nr_albumu = element.getNr_albumu();
				session.setAttribute("student", element);
				break;
			}
		}
		return imie;
	}

	public String ustawProfilDziekana() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");

		if (uzytkownik == null)
			return "moje_konto";

		Collection<Dziekan> dziekani = dziekanDAO.findAll();

		for (Dziekan element : dziekani) {
			if ((element.getUzytkownik().getId_uzytkownik().equals(uzytkownik.getId_uzytkownik()))) {
				id = element.getId_dziekan();
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				adres = element.getAdres();
				session.setAttribute("dziekan", element);
				break;
			}
		}
		return imie;
	}

	public String ustawProfilPracownika() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");

		if (uzytkownik == null)
			return "moje_konto";

		Collection<Pracownik_dydaktyczny> pracownicy = pracownikDydaktycznyDAO.findAll();

		for (Pracownik_dydaktyczny element : pracownicy) {

			if ((element.getUzytkownik().getId_uzytkownik().equals(uzytkownik.getId_uzytkownik()))) {
				id = element.getId_pracownik_dydaktyczny();
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				adres = element.getAdres();
				session.setAttribute("pracownik_dydaktyczny", element);
				break;

				/*
				 * Collection<Egzemplarz> egzemplarze = egzemplarzDAO.findAll(); iloscKsiazek =
				 * egzemplarze.size();
				 */

				/*
				 * Collection<Uzytkownik> uzytkownicy = uzytkownikDAO.findAll();
				 * zarejestrowaniUzytkownicy = uzytkownicy.size();
				 */
			}
		}
		return imie;
	}

	public String ustawProfilAdministratora() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Uzytkownik uzytkownik = (Uzytkownik) session.getAttribute("uzytkownik");

		if (uzytkownik == null)
			return "moje_konto";

		Collection<Administrator> administratorzy = administratorDAO.findAll();

		for (Administrator element : administratorzy) {
			if ((element.getUzytkownik().getId_uzytkownik().equals(uzytkownik.getId_uzytkownik()))) {
				id = element.getId_administrator();
				imie = element.getImie();
				nazwisko = element.getNazwisko();
				email = element.getEmail();
				pesel = element.getPesel();
				adres = element.getAdres();
				session.setAttribute("administrator", element);
				break;

				/*
				 * Collection<Egzemplarz> egzemplarze = egzemplarzDAO.findAll(); iloscKsiazek =
				 * egzemplarze.size();
				 */

				/*
				 * Collection<Uzytkownik> uzytkownicy = uzytkownikDAO.findAll();
				 * zarejestrowaniUzytkownicy = uzytkownicy.size();
				 */
			}
		}
		return imie;
	}
	
public String zaktualizuj() {
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		Uzytkownik uzytkownikSes = (Uzytkownik) session.getAttribute("uzytkownik");
		
		
	 	
//	 	Dziekan dziekan = (Dziekan) session.getAttribute("dziekan");
//	 	
//	 	Administrator administrator = (Administrator) session.getAttribute("administrator");
//	 	
//	 	Pracownik_dydaktyczny pracownik = (Pracownik_dydaktyczny) session.getAttribute("pracownik_dydaktyczny");
		
		
		if (uzytkownikSes.getRola().equals("STUDENT")) {
			Collection<Student> studenci = studentDAO.findAll();

			
			Student student = (Student) session.getAttribute("student");
			Uzytkownik uzytkownik = uzytkownikDAO.findOne(student.getUzytkownik().getId_uzytkownik());
			
			
			
			studenci.remove(student);
			for(Student element : studenci)
			{
				if(element.getUzytkownik().getLogin().equals(uzytkownikSes.getLogin()))
				{
					errorMessageEmail = "";
					errorMessageLogin = "Pracownik o podanym loginie ju¿ istnieje";
					errorMessage = "Pracownik o podanym loginie ju¿ istnieje";
					return errorMessageLogin;
				}
				if(element.getEmail().equals(email))
				{
					errorMessageLogin = "";
					errorMessageEmail = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					errorMessage = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					return errorMessageEmail;
				}
			}
			
				uzytkownik.setLogin(uzytkownikSes.getLogin());
			
			if(haslo.equals(""))
				uzytkownik.setHaslo(uzytkownik.getHaslo());
			else
				uzytkownik.setHaslo(haslo);
			
			
			if(uzytkownik.getRola().equals("STUDENT"))
				uzytkownik.setRola("STUDENT");
			else
				uzytkownik.setRola("BRAK");
			
			
			
			
			uzytkownik.setAktywowany(true);
			uzytkownik.setZalogowany(true);
			
			//student.setId_student(id);
			
			if(imie.equals(""))
				student.setImie(student.getImie());
			else
				student.setImie(imie);
			
			if(nazwisko.equals(""))
				student.setNazwisko(student.getNazwisko());
			else
				student.setNazwisko(nazwisko);
			
			if(email.equals(""))
				student.setEmail(student.getEmail());
			else
				student.setEmail(email);
			
			if(pesel.equals(""))
				student.setPesel(student.getPesel());
			else	
				student.setPesel(pesel);
			if(adres.equals(""))
				student.setAdres(student.getAdres());
			else
				student.setAdres(adres);
			
			
			if(nr_albumu.equals(""))
				student.setNr_albumu(student.getNr_albumu());
			else
				student.setNr_albumu(nr_albumu);
			
			
			student.setUzytkownik(uzytkownik);
			
			uzytkownikDAO.save(uzytkownik);
			studentDAO.save(student); 
			
			errorMessage = "Edytowano osobe: " + imie + " " + nazwisko;
			return "Mozna edytowac studenta";
			
		}
		
		if (uzytkownikSes.getRola().equals("DZIEKAN")) {
			Collection<Dziekan> dziekani = dziekanDAO.findAll();

			
			Dziekan dziekan = (Dziekan) session.getAttribute("dziekan");
			Uzytkownik uzytkownik = uzytkownikDAO.findOne(dziekan.getUzytkownik().getId_uzytkownik());
			
			
			
			dziekani.remove(dziekan);
			for(Dziekan element : dziekani)
			{
				if(element.getUzytkownik().getLogin().equals(uzytkownikSes.getLogin()))
				{
					errorMessageEmail = "";
					errorMessageLogin = "Pracownik o podanym loginie ju¿ istnieje";
					errorMessage = "Pracownik o podanym loginie ju¿ istnieje";
					return errorMessageLogin;
				}
				if(element.getEmail().equals(email))
				{
					errorMessageLogin = "";
					errorMessageEmail = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					errorMessage = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					return errorMessageEmail;
				}
			}
			
				uzytkownik.setLogin(uzytkownikSes.getLogin());
			
			if(haslo.equals(""))
				uzytkownik.setHaslo(uzytkownik.getHaslo());
			else
				uzytkownik.setHaslo(haslo);
			
			
			if(uzytkownik.getRola().equals("DZIEKAN"))
				uzytkownik.setRola("DZIEKAN");
			else
				uzytkownik.setRola("BRAK");
			
			
			
			
			uzytkownik.setAktywowany(true);
			uzytkownik.setZalogowany(true);
			
			if(imie.equals(""))
				dziekan.setImie(dziekan.getImie());
			else
				dziekan.setImie(imie);
			
			if(nazwisko.equals(""))
				dziekan.setNazwisko(dziekan.getNazwisko());
			else
				dziekan.setNazwisko(nazwisko);
			
			if(email.equals(""))
				dziekan.setEmail(dziekan.getEmail());
			else
				dziekan.setEmail(email);
			
			if(pesel.equals(""))
				dziekan.setPesel(dziekan.getPesel());
			else	
				dziekan.setPesel(pesel);
			if(adres.equals(""))
				dziekan.setAdres(dziekan.getAdres());
			else
				dziekan.setAdres(adres);
			
			
			dziekan.setUzytkownik(uzytkownik);
			
			uzytkownikDAO.save(uzytkownik);
			dziekanDAO.save(dziekan); 
			
			errorMessage = "Edytowano osobe: " + imie + " " + nazwisko;
			return "Mozna edytowac - dziekan";
			
		}
		
		if (uzytkownikSes.getRola().equals("ADMINISTRATOR")) {
			Collection<Administrator> admini = administratorDAO.findAll();

			
			Administrator admin = (Administrator) session.getAttribute("administrator");
			Uzytkownik uzytkownik = uzytkownikDAO.findOne(admin.getUzytkownik().getId_uzytkownik());
			
			
			
			admini.remove(admin);
			for(Administrator element : admini)
			{
				if(element.getUzytkownik().getLogin().equals(uzytkownikSes.getLogin()))
				{
					errorMessageEmail = "";
					errorMessageLogin = "Pracownik o podanym loginie ju¿ istnieje";
					errorMessage = "Pracownik o podanym loginie ju¿ istnieje";
					return errorMessageLogin;
				}
				if(element.getEmail().equals(email))
				{
					errorMessageLogin = "";
					errorMessageEmail = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					errorMessage = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					return errorMessageEmail;
				}
			}
			
				uzytkownik.setLogin(uzytkownikSes.getLogin());
			
			if(haslo.equals(""))
				uzytkownik.setHaslo(uzytkownik.getHaslo());
			else
				uzytkownik.setHaslo(haslo);
			
			
			if(uzytkownik.getRola().equals("ADMINISTRATOR"))
				uzytkownik.setRola("ADMINISTRATOR");
			else
				uzytkownik.setRola("BRAK");
			
			
			
			
			uzytkownik.setAktywowany(true);
			uzytkownik.setZalogowany(true);
			
			if(imie.equals(""))
				admin.setImie(admin.getImie());
			else
				admin.setImie(imie);
			
			if(nazwisko.equals(""))
				admin.setNazwisko(admin.getNazwisko());
			else
				admin.setNazwisko(nazwisko);
			
			if(email.equals(""))
				admin.setEmail(admin.getEmail());
			else
				admin.setEmail(email);
			
			if(pesel.equals(""))
				admin.setPesel(admin.getPesel());
			else	
				admin.setPesel(pesel);
			if(adres.equals(""))
				admin.setAdres(admin.getAdres());
			else
				admin.setAdres(adres);
			
			
			admin.setUzytkownik(uzytkownik);
			
			uzytkownikDAO.save(uzytkownik);
			administratorDAO.save(admin); 
			
			errorMessage = "Edytowano osobe: " + imie + " " + nazwisko;
			return "Mozna edytowac - administrator";
			
		}
		
		if (uzytkownikSes.getRola().equals("PRACOWNIK")) {
			Collection<Pracownik_dydaktyczny> pracownicy = pracownikDydaktycznyDAO.findAll();

			
			Pracownik_dydaktyczny pracownik = (Pracownik_dydaktyczny) session.getAttribute("pracownik_dydaktyczny");
			Uzytkownik uzytkownik = uzytkownikDAO.findOne(pracownik.getUzytkownik().getId_uzytkownik());
			
			
			
			pracownicy.remove(pracownik);
			for(Pracownik_dydaktyczny element : pracownicy)
			{
				if(element.getUzytkownik().getLogin().equals(uzytkownikSes.getLogin()))
				{
					errorMessageEmail = "";
					errorMessageLogin = "Pracownik o podanym loginie ju¿ istnieje";
					errorMessage = "Pracownik o podanym loginie ju¿ istnieje";
					return errorMessageLogin;
				}
				if(element.getEmail().equals(email))
				{
					errorMessageLogin = "";
					errorMessageEmail = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					errorMessage = "Pracownik o podanym adresie e-mail ju¿ istnieje";
					return errorMessageEmail;
				}
			}
			
				uzytkownik.setLogin(uzytkownikSes.getLogin());
			
			if(haslo.equals(""))
				uzytkownik.setHaslo(uzytkownik.getHaslo());
			else
				uzytkownik.setHaslo(haslo);
			
			
			if(uzytkownik.getRola().equals("PRACOWNIK"))
				uzytkownik.setRola("PRACOWNIK");
			else
				uzytkownik.setRola("BRAK");
			
			
			
			
			uzytkownik.setAktywowany(true);
			uzytkownik.setZalogowany(true);
			
			if(imie.equals(""))
				pracownik.setImie(pracownik.getImie());
			else
				pracownik.setImie(imie);
			
			if(nazwisko.equals(""))
				pracownik.setNazwisko(pracownik.getNazwisko());
			else
				pracownik.setNazwisko(nazwisko);
			
			if(email.equals(""))
				pracownik.setEmail(pracownik.getEmail());
			else
				pracownik.setEmail(email);
			
			if(pesel.equals(""))
				pracownik.setPesel(pracownik.getPesel());
			else	
				pracownik.setPesel(pesel);
			if(adres.equals(""))
				pracownik.setAdres(pracownik.getAdres());
			else
				pracownik.setAdres(adres);
			
			
			pracownik.setUzytkownik(uzytkownik);
			
			uzytkownikDAO.save(uzytkownik);
			pracownikDydaktycznyDAO.save(pracownik); 
			
			errorMessage = "Edytowano osobe: " + imie + " " + nazwisko;
			return "Mozna edytowac - pracownik";
			
		}
		
		
		
		
		
		return "should-work";
	}

}
