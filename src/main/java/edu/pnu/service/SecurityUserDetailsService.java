package edu.pnu.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
	private final MemberRepository memRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memRepo.findById(username).orElseThrow(() -> new UsernameNotFoundException("Not Found"));
		return new User(member.getId(), member.getPassword(), member.getAuthorities());
	}

}
