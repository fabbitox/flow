package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.entity.Criteria;
import edu.pnu.service.CriteriaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CriteriaController {
	private final CriteriaService criteriaService;
	
	@GetMapping("/criteria")
	public List<Criteria> select() {
		return criteriaService.findAll();
	}
}
