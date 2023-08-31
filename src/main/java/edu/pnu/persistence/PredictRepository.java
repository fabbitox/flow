package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Predict;

public interface PredictRepository extends JpaRepository<Predict, Integer> {

}
