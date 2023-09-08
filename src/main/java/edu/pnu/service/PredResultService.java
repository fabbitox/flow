package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.PredResult;
import edu.pnu.persistence.PredResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PredResultService {
	private final PredResultRepository predResultRepo;
	
	@Transactional
	public void insert(PredResult predict) {
		predResultRepo.save(predict);
	}
	
	public List<PredResult> findByIdpred(Integer idpred) {
		return predResultRepo.findByIdpred(idpred);
	}
}
