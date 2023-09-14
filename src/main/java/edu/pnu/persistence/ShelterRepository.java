package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.entity.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
	
}
