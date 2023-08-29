package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.Contact;
import edu.pnu.service.ContactService;

@RestController
public class ContactController {
	@Autowired ContactService contactService;
	
	@GetMapping("/contact/list")
	public List<Contact> selectAll() {
		return contactService.selectAll();
	}
	@PostMapping("/contact")
	public void insert(@RequestBody Contact contact) {
		contactService.insert(contact);
	}
	@DeleteMapping("/contact")
	public void delete(@RequestBody Contact contact) {
		contactService.delete(contact);
	}
}
