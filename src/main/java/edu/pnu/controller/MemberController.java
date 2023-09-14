package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.dto.MemberDTO;
import edu.pnu.entity.Member;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	
	@GetMapping("/contact/list")
	public List<MemberDTO> selectAll() {
		return memberService.findAll();
	}
	
	@PostMapping("/contact")
	public String addMember(@RequestBody Member member) {
		if (memberService.addMember(member) == null)
			return "이미 있는 연락처입니다";
		return "연락처 추가 성공";
	}
	
	@DeleteMapping("/contact/{id}")
	public void delete(@PathVariable Integer id) {
		memberService.deleteById(id);
	}
}
