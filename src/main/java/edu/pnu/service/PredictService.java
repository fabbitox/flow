package edu.pnu.service;

import java.util.List;

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
	public Predict insert(Predict predict) {
		return predictRepo.save(predict);
	}
	
	public List<Predict> findLast1h() {
		return predictRepo.findLast1h();
	}
}
