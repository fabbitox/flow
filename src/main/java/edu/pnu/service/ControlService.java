package edu.pnu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.dto.ControlDTO;
import edu.pnu.entity.Control;
import edu.pnu.persistence.ControlRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ControlService {
	private final ControlRepository controlRepo;
	
	public List<ControlDTO> ctrlPoint() {
		List<Control> controls = controlRepo.findByCtrlType(1);
		List<ControlDTO> controlDTOs = new ArrayList<>();
		for (Control control: controls)
			controlDTOs.add(new ControlDTO(control.getIdcriteria(), control.getLatitude(), control.getLongtitude()));
		return controlDTOs;
	}
	
	public List<ControlDTO> ctrlArea() {
		List<Control> controls = controlRepo.findByCtrlType(2);
		List<ControlDTO> controlDTOs = new ArrayList<>();
		for (Control control: controls)
			controlDTOs.add(new ControlDTO(control.getIdcriteria(), control.getLatitude(), control.getLongtitude()));
		return controlDTOs;
	}
}
