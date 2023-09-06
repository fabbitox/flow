package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class PredResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idresult;
	private Integer idpred;
	private Integer hour;
	private Double waterLevel;
	
	public PredResult(Integer idpred, Integer hour, Double waterLevel) {
		this.idpred = idpred;
		this.hour = hour;
		this.waterLevel = waterLevel;
	}
}
