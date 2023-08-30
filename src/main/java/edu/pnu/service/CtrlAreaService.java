package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.CtrlArea;
import edu.pnu.persistence.CtrlAreaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CtrlAreaService {
	private final CtrlAreaRepository ctrlAreaRepo;
	
	public List<CtrlArea> selectAll() {
		return ctrlAreaRepo.findAll();
	}
}
