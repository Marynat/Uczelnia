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

public class Informacje implements Domain {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_informacje;

	private String tytul;
	
	@Column(length = 1000)
	private String adres;
	
	@Override
	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

}