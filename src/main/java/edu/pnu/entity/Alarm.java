package edu.pnu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Alarm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idalarm;
	private Integer idmember;
	private Integer idpredResult;
	private Integer idcriteria;
	private LocalDateTime alarmDt;
	
	public Alarm(Integer idmember, Integer idpredResult, Integer idcriteria, LocalDateTime alarmDt) {
		this.idmember = idmember;
		this.idpredResult = idpredResult;
		this.idcriteria = idcriteria;
		this.alarmDt = alarmDt;
	}
}
