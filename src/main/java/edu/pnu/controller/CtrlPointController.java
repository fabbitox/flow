package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.CtrlPoint;
import edu.pnu.service.CtrlPointService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CtrlPointController {
	private final CtrlPointService ctrlPointService;
	
	@GetMapping("/ctrlpoint")
	public List<CtrlPoint> selectAll() {
		return ctrlPointService.selectAll();
	}
}
