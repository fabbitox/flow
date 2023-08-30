package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.CtrlArea;
import edu.pnu.service.CtrlAreaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CtrlAreaController {
	private final CtrlAreaService ctrlAreaService;
	
	@GetMapping("/ctrlarea")
	public List<CtrlArea> selectAll() {
		return ctrlAreaService.selectAll();
	}
}
