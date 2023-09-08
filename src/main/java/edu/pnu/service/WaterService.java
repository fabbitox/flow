package edu.pnu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Water;
import edu.pnu.persistence.WaterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaterService {
	private final WaterRepository waterRepo;
	
	@Transactional
	public void insert(Water water) {
		waterRepo.save(water);
	}
	
	public List<Water> findLast6h(LocalDateTime reqTime) {
		return waterRepo.findLast6h(reqTime);
	}
}
