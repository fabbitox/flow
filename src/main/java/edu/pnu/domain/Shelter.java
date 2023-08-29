package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Shelter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idshelter;
	private String name;
	private String address;
	private Double latitude;
	private Double longtitude;
}
