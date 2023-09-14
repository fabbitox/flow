package edu.pnu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PredResult {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idpredResult;
	private Integer idpredict;
	private Integer hour;
	private Double wl;
	public PredResult(Integer id, int hour, double wl) {
		idpredict = id;
		this.hour = hour;
		this.wl = wl;
	}
}
