package edu.pnu.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.domain.Water;

public interface WaterRepository extends JpaRepository<Water, Integer> {
	@Query(value = "select * from water where water_dt >= :reqTime - interval 6 hour and water_dt <= :reqTime", nativeQuery = true)
	public List<Water> findLast6h(LocalDateTime reqTime);
}
