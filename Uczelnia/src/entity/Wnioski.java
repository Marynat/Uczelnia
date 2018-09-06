package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


import java.io.Serializable;
import javax.persistence.*;

import dao.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Wnioski implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_wnioski;

	private String tytul;
	
	@Column(length = 1000)
	private String wiadomosc;
	private String status;
	
	@Column(nullable = true)
	private boolean zgoda;
	
	@ManyToOne(cascade = CascadeType.ALL,  optional = false)
	@JoinColumn(name = "pracownik_dydaktyczny_id_pracownik_dydaktyczny", nullable=true)
	private Pracownik_dydaktyczny pracownik;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "student_id_student", nullable=true)
	private Student student;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "dziekan_id_dziekan")
	private Dziekan dziekan;
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}