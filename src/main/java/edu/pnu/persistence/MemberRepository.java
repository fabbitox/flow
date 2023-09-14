package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
	public List<Member> findByIdIsNullOrIdIsNot(String id);
	public List<Member> findByContact(String contact);
	public void deleteByIdmember(Integer id);
	public Member findById(String id);
}
