package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class CtrlArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idarea;
	private Integer level;
	private Double latitude;
	private Double longtitude;
}
