package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.ControlDTO;
import edu.pnu.service.ControlService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ControlController {
	private final ControlService controlService;
	
	@GetMapping("/ctrlpoint")
	public List<ControlDTO> ctrlPoint() {
		return controlService.ctrlPoint();
	}
	
	@GetMapping("/ctrlarea")
	public List<ControlDTO> ctrlArea() {
		return controlService.ctrlArea();
	}
}
