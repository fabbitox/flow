package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Predict;

public interface PredictRepository extends JpaRepository<Predict, Integer> {
	@Query(value = "select * from predict where request_time >= now() - interval 1 hour", nativeQuery = true)
	public List<Predict> findLast1h();
}
