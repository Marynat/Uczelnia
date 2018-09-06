package entity;

import java.io.Serializable;
import javax.persistence.*;

import entity.Ocena.OcenaBuilder;
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

public class Ocena implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ocena;
	
	@ManyToMany
	@JoinTable(
		name="ocena_student"
		, joinColumns={
			@JoinColumn(name="ocena_ID_OCENA")
			}
		, inverseJoinColumns={
			@JoinColumn(name="student_ID_STUDENT")
			}
		)
	private List<Student> studenci;
	
	@OneToMany(mappedBy="ocena")
	private List<Przedmiot> przedmioty;
	
}