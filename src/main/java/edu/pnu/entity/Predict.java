package edu.pnu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Predict {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idpredict;
	private LocalDateTime predDt;
	private LocalDateTime reqDt;
	
	public Predict(LocalDateTime predDt, LocalDateTime reqDt) {
		this.predDt = predDt;
		this.reqDt = reqDt;
	}
}
