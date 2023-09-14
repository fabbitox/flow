package edu.pnu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Criteria {
	@Id
	private Integer idcriteria;
	private Double criteria;
}
