package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.entity.Criteria;
import edu.pnu.persistence.CriteriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CriteriaService {
	private final CriteriaRepository criteriaRepo;
	
	public List<Criteria> findAll() {
		return criteriaRepo.findAll();
	}
}
