package entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import dao.Domain;

@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Uzytkownik implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_uzytkownik;

	private String login;
	private String haslo;
	private String rola = null;

	private boolean zalogowany;
	private boolean aktywowany;
	
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}