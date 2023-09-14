package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.entity.Shelter;
import edu.pnu.service.ShelterService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShelterController {
	private final ShelterService shelterService;
	
	@GetMapping("/shelter")
	public List<Shelter> selectAll() {
		return shelterService.selectAll();
	}
	@PostMapping("/shelter")
	public void insert(@RequestBody Shelter shelter) {
		shelterService.addShelter(shelter);
	}
	@DeleteMapping("/shelter/{id}")
	public void delete(@PathVariable Integer id) {
		shelterService.delete(id);
	}
}
