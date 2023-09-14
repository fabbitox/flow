package edu.pnu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Control {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcontrol;
	private Integer idcriteria;
	private Integer ctrlType;
	private Double latitude;
	private Double longtitude;
}
