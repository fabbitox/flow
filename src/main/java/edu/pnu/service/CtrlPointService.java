package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.CtrlPoint;
import edu.pnu.persistence.CtrlPointRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CtrlPointService {
	private final CtrlPointRepository ctrlPointRepo;
	
	public List<CtrlPoint> selectAll() {
		return ctrlPointRepo.findAll();
	}
}
