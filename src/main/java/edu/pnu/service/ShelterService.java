package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Shelter;
import edu.pnu.persistence.ShelterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShelterService {
	private final ShelterRepository shelterRepo;
	
	public List<Shelter> selectAll() {
		return shelterRepo.findAll();
	}
	@Transactional
	public void insert(Shelter shelter) {
		shelterRepo.save(shelter);
	}
	@Transactional
	public void delete(Integer id) {
		shelterRepo.deleteById(id);
	}
}
