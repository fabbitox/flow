package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Shelter;
import edu.pnu.persistence.ShelterRepository;
import jakarta.transaction.Transactional;

@Service
public class ShelterService {
	@Autowired
	private ShelterRepository shelterRepo;
	
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
