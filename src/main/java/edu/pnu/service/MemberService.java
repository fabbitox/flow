package edu.pnu.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.dto.MemberDTO;
import edu.pnu.entity.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepo;
	private final PasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public List<MemberDTO> findAll() {
		List<Member> members = memberRepo.findByIdIsNullOrIdIsNot("admin");
		List<MemberDTO> memberDTOs = new ArrayList<>();
		for (Member member: members)
			memberDTOs.add(new MemberDTO(member.getIdmember(), member.getName(), member.getContact()));
		return memberDTOs;
	}
	@Transactional
	public Member addMember(Member member) {
		if (memberRepo.findByContact(member.getContact()).size() != 0)
			return null;
		String nowstr = LocalDateTime.now().toString();
		return memberRepo.save(Member.builder().id(nowstr).password(encoder.encode(nowstr)).name(member.getName()).contact(member.getContact()).build());
	}
	@Transactional
	public void deleteById(Integer id) {
		memberRepo.deleteByIdmember(id);
	}
}
