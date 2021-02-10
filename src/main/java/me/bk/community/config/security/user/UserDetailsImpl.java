package me.bk.community.config.security.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author : byungkyu
 * @date : 2021/02/10
 * @description :
 **/
public class UserDetailsImpl extends User {

	private Long id;
	private String nickName;

	public UserDetailsImpl(Long id, String username, String password, String nickName,
		Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
		this.nickName = nickName;
	}

	public Long getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}

}
