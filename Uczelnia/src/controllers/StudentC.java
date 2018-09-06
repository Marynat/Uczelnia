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

import com.oracle.wls.shaded.org.apache.xml.utils.Hashtree2Node;

import controllers.StudentC.Srednia;
import dao.StudentDAO;
import dao.AdministratorDAO;
import dao.PlatnosciDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.PracownikDydaktycznyDAO;
import dao.UzytkownikDAO;
import dao.WnioskiDAO;
import entity.Student;
import entity.Administrator;
import entity.Dziekan;
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

	public Collection<Semestr> pokazSemestry() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");

		Semestr semestr = new Semestr();
		
		Collection<Semestr> semestry = new LinkedList<>();
		
		semestry = student.getSemestry();

		return semestry;
	}

	public Collection<Przedmiot> pokazPrzedmioty() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");
		
		Collection<Przedmiot> przedmioty = new LinkedList<>();
		przedmioty = student.getPrzedmioty();
		
		

		return przedmioty;
	}

	@Data
	public class Srednia {
		public Semestr semestr;
		public float srednia = 0;
		public float dzielnik = 0;
		
		
		public Srednia(Semestr s, int i) {
			this.semestr = s;
			this.srednia = i;
		}
	}

//	public Collection<Srednia> sredniaS() {
//
//		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		Student student = (Student) session.getAttribute("student");
//
//		Collection<Srednia> srednieS = new ArrayList<Srednia>();
//		
//		Collection<Przedmiot> przedmioty = przedmiotDAO.findAll();	
//		Collection<Semestr> semestry = semestrDAO.findAll();
//		
//		Iterator<Semestr> iterS = semestry.iterator();
//		while(iterS.hasNext()) {
//			Semestr s = iterS.next();
//			Srednia ss = new Srednia(s,0);
//			srednieS.add(ss);
//		}
//		
//		
//
//		Iterator<Przedmiot> iter = przedmioty.iterator();
//		Iterator<Srednia> iter2 = srednieS.iterator();
//
//		while (iter.hasNext()) {
//			Przedmiot p = iter.next();
//			if (!p.getSemestr().getStudent().getId_student().equals(student.getId_student())) {
//				iter.remove();
//			}
//			while (iter2.hasNext()) {
//				Srednia s = iter2.next();
//				if (s.getSemestr().getId_semestr().equals(p.getSemestr().getId_semestr())) {
//					s.srednia += p.getOcena();
//					s.dzielnik ++;
//				}
//			}	
//		}
//		//TODO its broken so far...
//		while(iter2.hasNext()) {
//			Srednia s = iter2.next();
//			if (!s.getSemestr().getStudent().getId_student().equals(student.getId_student())) {
//				iter.remove();
//			}
//			s.srednia = s.srednia / s.dzielnik;
//		}
//
//		return srednieS;
//	}
	
	public Collection<Platnosci> pokazPlatnosci(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");
		
		Collection<Platnosci> platnosci = platnosciDAO.findAll();
		Iterator<Platnosci> iter = platnosci.iterator();
		
		while(iter.hasNext()) {
			Platnosci p = iter.next();
			if(!p.getStudent().getId_student().equals(student.getId_student())) {
				iter.remove();
			}
			
		}
		
		return platnosci;
	}
	
	public Collection<Wnioski> pokazWnioski(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Student student = (Student) session.getAttribute("student");
		
		Collection<Wnioski> wnioski = wnioskiDAO.findAll();
		Iterator<Wnioski> iter = wnioski.iterator();
		
		while(iter.hasNext()) {
			Wnioski p = iter.next();
			if(!p.getStudent().getId_student().equals(student.getId_student())) {
				iter.remove();
			}
			
		}
		
		return wnioski;
		
	}
}
