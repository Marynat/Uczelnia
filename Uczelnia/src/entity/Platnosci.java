package entity;

import java.io.Serializable;
import javax.persistence.*;

import dao.Domain;
import entity.Platnosci.PlatnosciBuilder;
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

public class Platnosci implements Domain {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_platnosci;
	
	private String tytul;
	private float cena;
	private String status;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "administrator_id_administrator")
	private Administrator admin;
	
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "student_id_student")
	private Student student;

	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}
}