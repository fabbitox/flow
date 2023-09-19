package edu.pnu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.entity.PredResult;
import edu.pnu.persistence.PredResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredResultService {
	private final PredResultRepository predResultRepo;
	
	@Transactional
	public void addPredResult(PredResult predict) {
		predResultRepo.save(predict);
	}
	
	public List<PredResult> findByIdpredict(Integer idpred) {
		return predResultRepo.findByIdpredict(idpred);
	}
	
	public List<Integer> findWarning(Integer idpredict) {
		return predResultRepo.findWarning(idpredict);
	}
	
	public List<Integer> findDanger(Integer idpredict) {
		return predResultRepo.findDanger(idpredict);
	}
	
	public List<Double> findLast6h(LocalDateTime start) {
		return predResultRepo.findLast6h(start);
	}
}
