package entity;

import java.io.Serializable;
import javax.persistence.*;

import dao.Domain;
import entity.Student.StudentBuilder;
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

public class Pracownik_dydaktyczny implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_pracownik_dydaktyczny;
	private String imie;
	private String nazwisko;
	private String pesel;
	
	private String adres;
	private String email;
	
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "uzytkownik_id_uzytkownik")
	private Uzytkownik uzytkownik;
	
	@ManyToMany(mappedBy="pracownicy_dydaktyczni")
	private List<Przedmiot> przedmioty;

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}