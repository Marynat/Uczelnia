package entity;

import java.io.Serializable;
import javax.persistence.*;

import dao.Domain;
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

public class Dziekan implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_dziekan;

	private String imie;
	private String nazwisko;
	private String pesel;
	private String email;
	private String adres;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "uzytkownik_id_uzytkownik")
	private Uzytkownik uzytkownik;
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}