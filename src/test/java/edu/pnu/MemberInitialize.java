package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.entity.Member;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class MemberInitialize {
	@Autowired
	MemberRepository memberRepo;
	PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	public void doWork() {
		memberRepo.save(Member.builder().id("admin").password(encoder.encode("abcd")).build());
	}
}
