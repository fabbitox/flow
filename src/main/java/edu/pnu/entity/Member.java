package edu.pnu.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idmember;
	private String name;
	private String contact;
	private String id;
	private String password;
	private String role;
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(role);
	}
}
