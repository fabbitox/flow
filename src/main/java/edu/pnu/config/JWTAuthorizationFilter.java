package edu.pnu.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	private MemberRepository memRepo;
	
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memRepo) {
		super(authenticationManager);
		this.memRepo = memRepo;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// header에 Authorization이 없거나 "Bearer "로 시작하지 않으면 doFilter, return
		String srcToken = request.getHeader("Authorization");
		if (srcToken == null || !srcToken.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}// 해석해서 user를 찾는다
		String jwtToken = srcToken.replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwtkey")).build().verify(jwtToken).getClaim("username").asString();
		Optional<Member> opt = memRepo.findById(username);
		if (!opt.isPresent()) {
			chain.doFilter(request, response);
			return;
		}// 인증
		Member findmember = opt.get();
		User user = new User(findmember.getId(), findmember.getPassword(), findmember.getAuthorities());
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(request, response);
	}
}
