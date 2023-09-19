package edu.pnu.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.entity.PredResult;

public interface PredResultRepository extends JpaRepository<PredResult, Integer> {
	public List<PredResult> findByIdpredict(Integer idpred);
	@Query(value = "SELECT idpred_result FROM pred_result where wl > (select criteria from criteria where idcriteria = 1) and wl <= (select criteria from criteria where idcriteria = 2) and idpredict = :idpredict", nativeQuery = true)
	public List<Integer> findWarning(Integer idpredict);
	@Query(value = "SELECT idpred_result FROM pred_result where wl > (select criteria from criteria where idcriteria = 2) and idpredict = :idpredict", nativeQuery = true)
	public List<Integer> findDanger(Integer idpredict);
	@Query(value = "select wl from (select distinct DATE_FORMAT(pred_dt + interval hour hour, '%Y-%m-%d %H:%i') as dt, avg(wl) as wl "
			+ "	from predict, pred_result "
			+ "	where predict.idpredict = pred_result.idpredict "
			+ "    group by dt) as t1 "
			+ "where t1.dt > :time", nativeQuery = true)
	public List<Double> findLast6h(LocalDateTime time);
}
