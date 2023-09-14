package edu.pnu.service;

import org.springframework.stereotype.Service;

import edu.pnu.entity.Predict;
import edu.pnu.persistence.PredictRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredictService {
	private final PredictRepository predictRepo;
	
	@Transactional
	public Predict addPredict(Predict predict) {
		return predictRepo.save(predict);
	}

	public Predict findLast() {
		return predictRepo.findTop1ByOrderByIdpredictDesc();
	}
}
