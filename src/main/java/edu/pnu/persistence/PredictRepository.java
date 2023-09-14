package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.entity.Predict;

public interface PredictRepository extends JpaRepository<Predict, Integer> {
	public Predict findTop1ByOrderByIdpredictDesc();
}
