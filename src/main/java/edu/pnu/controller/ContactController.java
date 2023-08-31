package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Contact;
import edu.pnu.service.ContactService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContactController {
	private final ContactService contactService;
	
	@GetMapping("/contact/list")
	public List<Contact> selectAll() {
		return contactService.selectAll();
	}
	@PostMapping("/contact")
	public void insert(@RequestBody Contact contact) {
		contactService.insert(contact);
	}
	@DeleteMapping("/contact/{id}")
	public void delete(@PathVariable Integer id) {
		contactService.deleteById(id);
	}
}
