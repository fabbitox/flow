package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.PredResult;

public interface PredResultRepository extends JpaRepository<PredResult, Integer> {

}
