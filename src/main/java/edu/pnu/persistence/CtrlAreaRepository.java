package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.CtrlArea;

public interface CtrlAreaRepository extends JpaRepository<CtrlArea, Integer> {
	
}
