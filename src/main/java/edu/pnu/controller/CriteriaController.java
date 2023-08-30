package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import edu.pnu.domain.Criteria;
import edu.pnu.service.CriteriaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CriteriaController {
	private final CriteriaService criteriaService;
	
	@GetMapping("/criteria")
	public List<Criteria> select() {
		return criteriaService.findAll();
	}
}
