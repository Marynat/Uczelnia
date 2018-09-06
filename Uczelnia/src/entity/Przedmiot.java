package entity;

import java.io.Serializable;
import javax.persistence.*;

import entity.Przedmiot.PrzedmiotBuilder;
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

public class Przedmiot implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_przedmiot;
	
	private String nazwa;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "semestr_id_semestr")
	private Semestr semestr;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ocena_id_ocena")
	private Ocena ocena;
	
	@ManyToMany
	@JoinTable(
		name="przedmiot_pracownik_dydaktyczny"
		, joinColumns={
			@JoinColumn(name="Przedmiot_ID_PRZEDMIOT")
			}
		, inverseJoinColumns={
			@JoinColumn(name="pracownik_ID_PRACOWNIK_DYDAKTYCZNY")
			}
		)
	private List<Pracownik_dydaktyczny> pracownicy_dydaktyczni;
	
	@ManyToMany
	@JoinTable(
		name="przedmiot_student"
		, joinColumns={
			@JoinColumn(name="Przedmiot_ID_PRZEDMIOT")
			}
		, inverseJoinColumns={
			@JoinColumn(name="student_ID_STUDENT")
			}
		)
	private List<Student> studenci;

}