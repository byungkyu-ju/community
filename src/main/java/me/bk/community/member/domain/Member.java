package me.bk.community.member.domain;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String password;
	private String nickName;


	@Builder
	public Member(String email, String password, String nickName) {
		this.email = email;
		this.password = password;
		this.nickName = nickName;
	}

	public Member(String nickName) {
		this.nickName = nickName;
	}

	public boolean isEqualPassword(String passwordConfirm) {
		return this.password.equals(passwordConfirm);
	}

	public void checkPassword(String password) {
		if(!this.password.equals(password)){
			throw new IllegalArgumentException("비밀번호 오류");
		}
	}

	public List<GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(Role.ROLE_MEMBER.name()));
	}

	public void update(Member member) {
		this.nickName = member.nickName;
	}
}
