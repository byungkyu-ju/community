package me.bk.community.config.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.bk.community.member.application.MemberService;
import me.bk.community.member.domain.Member;

/**
 * @author : byungkyu
 * @date : 2021/02/10
 * @description :
 **/
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberService.findByEmail(username);
		return new UserDetailsImpl(member.getId(), member.getEmail(), member.getPassword(), member.getNickName(),
			member.getAuthorities());
	}
}
