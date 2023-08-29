package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.CtrlPoint;

public interface CtrlPointRepository extends JpaRepository<CtrlPoint, Integer> {
	
}
