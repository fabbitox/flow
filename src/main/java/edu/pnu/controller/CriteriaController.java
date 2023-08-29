package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Criteria;
import edu.pnu.service.CriteriaService;

@RestController
public class CriteriaController {
	@Autowired
	private CriteriaService criteriaService;
	
	@GetMapping("/criteria")
	public List<Criteria> select() {
		return criteriaService.findAll();
	}
}
