package controllers;

import java.util.Collection;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import controllers.StudentC.OcenaPrzedmiotu;
import dao.OcenaDAO;
import dao.PlatnosciDAO;
import dao.PrzedmiotDAO;
import dao.SemestrDAO;
import dao.StudentDAO;
import dao.WnioskiDAO;
import entity.Ocena;
import entity.Pracownik_dydaktyczny;
import entity.Przedmiot;
import entity.Student;
import entity.Wnioski;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

	@EJB
	private OcenaDAO ocenaDAO;
	
	
	@Getter
	@Setter
	private Student student = new Student();
	
	@Getter
	private Map<Long, Student> transferredValues = new HashMap<Long, Student>();
	
	private String tytul = "";
	
	private String wiadomosc = "";

	@Data
	public class OcenaPrzedmiotu {
		public int ocena;
		public String przedmiotNazwa = "";
		public long idSem = 0;
	}

	public Collection<OcenaPrzedmiotu> pokazPrzedmioty() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pracownik_dydaktyczny pracownik = (Pracownik_dydaktyczny) session.getAttribute("pracownik_dydaktyczny");

		Collection<OcenaPrzedmiotu> oc = new LinkedList<>();

		Collection<Przedmiot> przedmioty = new LinkedList<>();
		przedmioty = pracownik.getPrzedmioty();
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
					if (/*
						 * o.getPrzedmiot().getPracownicy_dydaktyczni().get(pracownik.
						 * getId_pracownik_dydaktyczny()).getId_pracownik_dydaktyczny()
						 */ 1 == (pracownik.getId_pracownik_dydaktyczny())) {

						oce.setOcena(o.getOcena());

					}
					oc.add(oce);
				}
			}
		}
		return oc;
	}

	public Collection<Wnioski> pokazWnioski() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		Pracownik_dydaktyczny pracownik = (Pracownik_dydaktyczny) session.getAttribute("pracownik_dydaktyczny");

		Collection<Wnioski> wnioski = wnioskiDAO.findAll();
		Iterator<Wnioski> iter = wnioski.iterator();

		while (iter.hasNext()) {
			Wnioski p = iter.next();

			if (p.getPracownik() == null) {
				iter.remove();
			} else {

				if (!p.getPracownik().getId_pracownik_dydaktyczny().equals(pracownik.getId_pracownik_dydaktyczny())) {
					iter.remove();
				}
			}

		}

		return wnioski;

	}
	public void wyslijW() {
		
		student.setId_student((long) 1);
		student = studentDAO.findOne(student.getId_student());
		
		
		
		String smtpHostServer = "smtp.example.com";
	    String emailID = student.getEmail();
	    
	    Properties props = System.getProperties();

	    props.put("mail.smtp.host", smtpHostServer);
		
		Session session = Session.getInstance(props, null);
		
		try
		    {
		      MimeMessage msg = new MimeMessage(session);
		      //set message headers
		      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		      msg.addHeader("format", "flowed");
		      msg.addHeader("Content-Transfer-Encoding", "8bit");

		      msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));

		      msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));

		      msg.setSubject(tytul, "UTF-8");

		      msg.setText(wiadomosc, "UTF-8");

		      msg.setSentDate(new Date());

		      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailID, false));
		      System.out.println("Message is ready");
	    	  Transport.send(msg);  

		      System.out.println("EMail Sent Successfully!!");
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
		
	}

}
