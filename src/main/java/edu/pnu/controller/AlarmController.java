package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.AlarmDTO;
import edu.pnu.service.AlarmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AlarmController {
	private final AlarmService alarmService;
	
	@GetMapping("/alarm")
	public List<AlarmDTO> findAll() {
		return alarmService.findAll();
	}
}
