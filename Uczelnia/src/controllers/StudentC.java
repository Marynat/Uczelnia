package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.jpa.jpql.parser.WhereClause;

import com.oracle.wls.shaded.org.apache.xml.utils.Hashtree2Node;

import dao.StudentDAO;
import dao.AdministratorDAO;
import dao.OcenaDAO;
import dao.PlatnosciDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.PracownikDydaktycznyDAO;
import dao.UzytkownikDAO;
import dao.WnioskiDAO;
import entity.Student;
import entity.Administrator;
import entity.Dziekan;
import entity.Ocena;
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
public class StudentC {

	@EJB
	private StudentDAO studentDAO;

	@EJB
	private PrzedmiotDAO przedmiotDAO;

	@EJB
	private SemestrDAO semestrDAO;

	@EJB
	private PlatnosciDAO platnosciDAO;

	@EJB
	private WnioskiDAO wnioskiDAO;

	@EJB
	private OcenaDAO ocenaDAO;

	public Collection<Semestr> pokazSemestry() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");

		Semestr semestr = new Semestr();

		Collection<Semestr> semestry = new LinkedList<>();

		semestry = student.getSemestry();

		return semestry;
	}

	@Data
	public class OcenaPrzedmiotu {
		public int ocena;
		public String przedmiotNazwa = "";
		public long idSem = 0;
	}

	public Collection<OcenaPrzedmiotu> pokazPrzedmioty() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");

		Collection<OcenaPrzedmiotu> oc = new LinkedList<>();

		Collection<Przedmiot> przedmioty = new LinkedList<>();
		przedmioty = student.getPrzedmioty();
		Collection<Ocena> oceny = ocenaDAO.findAll();

		Iterator<Przedmiot> it = przedmioty.iterator();
		while (it.hasNext()) {
			Przedmiot p = it.next();
			Iterator<Ocena> it2 = oceny.iterator();
			while (it2.hasNext()) {
				Ocena o = it2.next();
				if (p.getId_przedmiot().equals(o.getPrzedmiot().getId_przedmiot())) {
					OcenaPrzedmiotu oce = new OcenaPrzedmiotu();
					oce.setPrzedmiotNazwa(p.getNazwa());
					oce.setIdSem(p.getSemestr().getId_semestr());
					if (o.getStudent().getId_student().equals(student.getId_student())) {

						oce.setOcena(o.getOcena());
						oc.add(oce);

					}
				}
			}
		}
		return oc;
	}

	@Data
	public class Srednie {
		public float srednia;
		public float dodana;
		public float dzielnik;
		public long idSem;
	}

	public Collection<Srednie> sredniaS() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");

		Collection<OcenaPrzedmiotu> oc = new LinkedList<>();
		Collection<Srednie> sr = new LinkedList<>();

		Collection<Przedmiot> przedmioty = new LinkedList<>();
		przedmioty = student.getPrzedmioty();
		Collection<Ocena> oceny = ocenaDAO.findAll();

		Iterator<Przedmiot> it = przedmioty.iterator();
		while (it.hasNext()) {
			Przedmiot p = it.next();
			Iterator<Ocena> it2 = oceny.iterator();
			while (it2.hasNext()) {
				Ocena o = it2.next();
				if (p.getId_przedmiot().equals(o.getPrzedmiot().getId_przedmiot())) {
					OcenaPrzedmiotu oce = new OcenaPrzedmiotu();
					oce.setPrzedmiotNazwa(p.getNazwa());
					oce.setIdSem(p.getSemestr().getId_semestr());
					if (o.getStudent().getId_student().equals(student.getId_student())) {

						oce.setOcena(o.getOcena());

						oc.add(oce);
					}
				}
			}
		}

		Iterator<OcenaPrzedmiotu> it3 = oc.iterator();
		while (it3.hasNext()) {
			OcenaPrzedmiotu oce = it3.next();
			if (sr.isEmpty()) {
				Srednie sre = new Srednie();
				sre.setIdSem(oce.idSem);
				sre.setDodana(oce.getOcena());
				sre.setDzielnik(1);
				sr.add(sre);
			}
			Iterator<Srednie> it4 = sr.iterator();
			while (it4.hasNext()) {
				Srednie sred = it4.next();
				if (oce.idSem == sred.idSem) {
					sred.setDodana(sred.getDodana() + oce.getOcena());
					sred.setDzielnik(sred.getDzielnik() + 1);
				} else {
					sred.setIdSem(oce.getIdSem());
					sred.setDodana(oce.getOcena());
					sred.setDzielnik(1);
					// sr.add(sred);

					// TODO jak starczy czasu czyli nie...
				}
			}

		}

		Iterator<Srednie> sup = sr.iterator();
		while (sup.hasNext()) {
			Srednie ser = sup.next();
			ser.srednia = ser.dodana / ser.dzielnik;
		}

		return sr;
	}

	public Collection<Platnosci> pokazPlatnosci() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");

		Collection<Platnosci> platnosci = platnosciDAO.findAll();
		Iterator<Platnosci> iter = platnosci.iterator();

		while (iter.hasNext()) {
			Platnosci p = iter.next();
			if (!p.getStudent().getId_student().equals(student.getId_student())) {
				iter.remove();
			}

		}

		return platnosci;
	}

	public Collection<Wnioski> pokazWnioski() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");

		Collection<Wnioski> wnioski = wnioskiDAO.findAll();
		Iterator<Wnioski> iter = wnioski.iterator();

		while (iter.hasNext()) {
			Wnioski p = iter.next();

			if (p.getStudent() == null) {
				iter.remove();
			} else {
				if (!p.getStudent().getId_student().equals(student.getId_student())) {
					iter.remove();
				}
			}
		}

		return wnioski;

	}

	public Collection<Student> pokazStudentow() {
		return studentDAO.findAll();
	}
	
	public Collection<Wnioski> pokazWnioskiDz() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Dziekan dziekan = (Dziekan) session.getAttribute("dziekan");

		Collection<Wnioski> wnioski = wnioskiDAO.findAll();
		Iterator<Wnioski> iter = wnioski.iterator();

		while (iter.hasNext()) {
			Wnioski p = iter.next();

			if (p.getDziekan() == null) {
				iter.remove();
			} else {
				if (!p.getDziekan().getId_dziekan().equals(dziekan.getId_dziekan())) {
					iter.remove();
				}
				if(p.getStatus().equals("ROZPATRZONE")) {
					iter.remove();
				}
			}
		}

		return wnioski;

	}
	
	public void updateWnioski(Collection<Wnioski> wnioski) {
		
		wnioski = wnioskiDAO.findAll();
		
		Iterator<Wnioski> it = wnioski.iterator();
		while(it.hasNext()) {
			Wnioski w = it.next();
			w.setStatus("ROZPATRZONE");
			
			wnioskiDAO.save(w);
		}
		
	}
}
