package edu.pnu.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Predict {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idpredict;
	private LocalDateTime predDatetime;
	private LocalDateTime requestTime;
	
	public Predict(LocalDateTime predDatetime, LocalDateTime requestTime) {
		this.predDatetime = predDatetime;
		this.requestTime = requestTime;
	}
}
