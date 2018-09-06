package entity;

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

public class Uczelnia implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_uczelnia;

	private String nazwa;
	private String adres;
	private boolean rekrutacja;
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}