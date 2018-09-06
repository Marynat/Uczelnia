package entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

public class Kandydat implements Domain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_kandydat;

	private String imie;
	private String nazwisko;
	private String pesel;
	private float srednia;
	private String szkola;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "dziekan_id_dziekan")
	private Dziekan dziekan;

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}