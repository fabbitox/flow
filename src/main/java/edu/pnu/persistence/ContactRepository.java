package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	public Contact findByNameAndContact(String name, String contact);

	public void deleteByIdcontact(Integer id);
}
