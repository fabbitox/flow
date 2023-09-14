package edu.pnu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Shelter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idshelter;
	private Integer idcriteria;
	private String name;
	private String address;
	private Double latitude;
	private Double longtitude;
	
	public void setIdcriteria(int idcriteria) {
		this.idcriteria = idcriteria;
	}
}
