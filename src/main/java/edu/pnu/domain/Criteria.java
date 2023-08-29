package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Criteria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcriteria;
	private Double warning;
	private Double danger;
}
