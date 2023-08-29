package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.CtrlArea;
import edu.pnu.persistence.CtrlAreaRepository;

@Service
public class CtrlAreaService {
	@Autowired
	private CtrlAreaRepository ctrlAreaRepo;
	
	public List<CtrlArea> selectAll() {
		return ctrlAreaRepo.findAll();
	}
}
