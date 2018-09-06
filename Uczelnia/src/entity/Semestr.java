package entity;

import java.io.Serializable;
import javax.persistence.*;

import entity.Semestr.SemestrBuilder;
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

public class Semestr implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_semestr;
	
	@ManyToMany(mappedBy="semestry")
	private List<Student> students;
	
	@OneToMany(mappedBy="semestr")
	private List<Przedmiot> przedmioty;
	
}