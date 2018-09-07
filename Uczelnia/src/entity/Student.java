package entity;

import java.io.Serializable;
import javax.persistence.*;

import dao.Domain;
import entity.Uzytkownik.UzytkownikBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Student implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_student;

	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String adres;

	private String nr_albumu;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "uzytkownik_id_uzytkownik")
	private Uzytkownik uzytkownik;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name="student_semestr"
			, joinColumns={
				@JoinColumn(name="Student_ID_STUDENT")
				}
			, inverseJoinColumns={
				@JoinColumn(name="semestr_ID_SEMESTR")
				}
			)
	private List<Semestr> semestry;
	
	
	@ManyToMany(mappedBy="studenci")
	private List<Przedmiot> przedmioty;
	
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}