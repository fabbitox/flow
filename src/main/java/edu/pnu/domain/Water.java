package edu.pnu.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Water {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idwater;
	private LocalDateTime waterDt;
	private Double waterlevel;
	
	public Water() {}
	public Water(LocalDateTime waterDt, Double waterlevel) {
		this.waterDt = waterDt;
		this.waterlevel = waterlevel;
	}
}
