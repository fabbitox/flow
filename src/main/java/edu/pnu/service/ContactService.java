package edu.pnu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.pnu.domain.Contact;
import edu.pnu.persistence.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {
	private final ContactRepository contactRepo;
	
	public List<Contact> selectAll() {
		return contactRepo.findAll();
	}
	@Transactional
	public void insert(Contact contact) {
		contactRepo.save(contact);
	}
	@Transactional
	public void deleteById(Integer id) {
		contactRepo.deleteByIdcontact(id);
	}
}
