package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.CtrlPoint;
import edu.pnu.service.CtrlPointService;

@RestController
public class CtrlPointController {
	@Autowired
	private CtrlPointService ctrlPointService;
	
	@GetMapping("/ctrlpoint")
	public List<CtrlPoint> selectAll() {
		return ctrlPointService.selectAll();
	}
}
