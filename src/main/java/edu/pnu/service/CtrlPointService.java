package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.CtrlPoint;
import edu.pnu.persistence.CtrlPointRepository;

@Service
public class CtrlPointService {
	@Autowired
	private CtrlPointRepository ctrlPointRepo;
	
	public List<CtrlPoint> selectAll() {
		return ctrlPointRepo.findAll();
	}
}
