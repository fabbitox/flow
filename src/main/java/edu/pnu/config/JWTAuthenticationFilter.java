package edu.pnu.config;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		// 인증을 위한 토큰 생성해서 인증 요청, 성공하면 세션에 등록
		// request의 body에 JSON으로 담겨오는 username/password 읽어서 Member 객체로
		ObjectMapper om = new ObjectMapper();
		try {
			Member member = om.readValue(request.getInputStream(), Member.class);
			Authentication authToken = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword());
			Authentication auth = authenticationManager.authenticate(authToken);
			log.info("Authenticated: [" + member.getId() + "]");
			return auth;
		} catch (Exception e) {
			log.info("Not Authenticated: " + e.getMessage());
		}
		return null;
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// JWT 토큰 만들어서 response의 Header에 등록
		User user = (User) authResult.getPrincipal();
		log.info("successfulAuthentication: " + user.toString());
		// JWT 생성
		String jwtToken = JWT.create()
				.withClaim("username", user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
				.sign(Algorithm.HMAC256("edu.pnu.jwtkey"));
		// header 추가
		// Bearer: JWT라는 것을 나타냄
		response.addHeader("Authorization", "Bearer " + jwtToken);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"message\":\"로그인 성공\"}");      
		response.getWriter().flush(); // 출력 스트림 플러시
		response.getWriter().close(); // 출력 스트림 종료
//		chain.doFilter(request, response);
	}
}
