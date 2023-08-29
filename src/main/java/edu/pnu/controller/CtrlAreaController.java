package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.CtrlArea;
import edu.pnu.service.CtrlAreaService;

@RestController
public class CtrlAreaController {
	@Autowired
	private CtrlAreaService ctrlAreaService;
	
	@GetMapping("/ctrlarea")
	public List<CtrlArea> selectAll() {
		return ctrlAreaService.selectAll();
	}
}
