package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Criteria;
import edu.pnu.persistence.CriteriaRepository;

@Service
public class CriteriaService {
	@Autowired
	private CriteriaRepository criteriaRepo;
	
	public List<Criteria> findAll() {
		return criteriaRepo.findAll();
	}
}
