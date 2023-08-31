package edu.pnu.service;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Predict;
import edu.pnu.persistence.PredictRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredictService {
	private final PredictRepository predictRepo;
	
	@Transactional
	public void insert(Predict predict) {
		predictRepo.save(predict);
	}
}
