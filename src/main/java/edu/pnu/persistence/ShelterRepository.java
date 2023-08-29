package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
	
}
