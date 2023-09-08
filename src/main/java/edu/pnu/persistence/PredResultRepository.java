package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.PredResult;

public interface PredResultRepository extends JpaRepository<PredResult, Integer> {
	public List<PredResult> findByIdpred(Integer idpred);
}
