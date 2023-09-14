package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pnu.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
	@Query(value = "select name, contact, wl, idcriteria, pred_dt + interval hour hour as wl_dt, alarm_dt "
			+ "from alarm, member, pred_result, predict "
			+ "where alarm.idmember = member.idmember and alarm.idpred_result = pred_result.idpred_result and pred_result.idpredict = predict.idpredict "
			+ "order by alarm_dt desc", nativeQuery = true)
	public List<Object[]> getLogs();
}
