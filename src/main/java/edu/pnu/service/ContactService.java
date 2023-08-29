package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Contact;
import edu.pnu.persistence.ContactRepository;
import jakarta.transaction.Transactional;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;
	
	public List<Contact> selectAll() {
		return contactRepo.findAll();
	}
	@Transactional
	public void insert(Contact contact) {
		contactRepo.save(contact);
	}
	@Transactional
	public void delete(Contact contact) {
		Integer id = contactRepo.findByNameAndContact(contact.getName(), contact.getContact()).getIdcontact();
		contactRepo.deleteById(id);
	}
}
