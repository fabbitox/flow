package edu.pnu.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import edu.pnu.persistence.MemberRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private  AuthenticationConfiguration authConfig;
	@Autowired
	private MemberRepository memberRepository;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // CORS 설정위한 메소드. 다른 도메인의 요청 허용을 사용자가 설정.
	   public CorsConfigurationSource corsConfigurationSource() {
	      CorsConfiguration configuration = new CorsConfiguration(); // CORS 설정위한 객체 생성
	      configuration.setAllowedOriginPatterns(Arrays.asList("*")); // 허용 도메인(출처, origin) 설정. 3000만 허용.
	      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
	      configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
	      configuration.setAllowCredentials(true); // 자격증명(쿠키, HTTP인증) 요청 허용.

	      configuration.addExposedHeader("Authorization"); // 클라이언트에서 Authorization 헤더 값 읽을 수 있게 해준다.

	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // URL 기반의 CORS 설정을 위한 객체 생성.
	      source.registerCorsConfiguration("/**", configuration); // 모든 URL(/**)에 대해 위에서 정의한 configuration의 CORS 설정을 적용한다.
	      return source;
	   }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());// CSRF 보호 비활성화(JS에서 호출 가능)
//		http.cors(cors -> cors.disable());// CORF 보호 비활성화(React에서 호출 가능) - RestAPI
		http.cors(cors -> {
	         cors.configurationSource(corsConfigurationSource());
	      });
		http.authorizeHttpRequests(security -> {
			security.requestMatchers("/contact/list").authenticated().anyRequest().permitAll();
//			security.anyRequest().permitAll();
		});
		
		http.formLogin(frmLogin -> frmLogin.disable());// Form을 이용한 로그인 사용하지 않겠다
		// 시큐리티 세션을 만들지 않겠다
		http.sessionManagement(ssmg -> ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilter(new JWTAuthenticationFilter(authConfig.getAuthenticationManager()));
		http.addFilter(new JWTAuthorizationFilter(authConfig.getAuthenticationManager(), memberRepository));
		return http.build();
	}
}
