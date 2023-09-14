package edu.pnu.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.dto.AlarmDTO;
import edu.pnu.entity.Alarm;
import edu.pnu.persistence.AlarmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final AlarmRepository alarmRepo;
	
	@Transactional
	public void sendAlarm(Alarm alarm) {
		alarmRepo.save(alarm);
	}
	
	public List<AlarmDTO> findAll() {
		List<Object[]> alarms = alarmRepo.getLogs();
		List<AlarmDTO> alarmDTOs = new ArrayList<>();
		for (Object[] alarm: alarms)
			alarmDTOs.add(new AlarmDTO((String)alarm[0], (String)alarm[1], (Double)alarm[2], (Integer)alarm[3], ((Timestamp)alarm[4]).toLocalDateTime()));
		return alarmDTOs;
	}
}
