package me.bk.community.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.bk.community.auth.dto.LoginTokenRequest;
import me.bk.community.auth.dto.LoginTokenResponse;
import me.bk.community.auth.infra.JwtTokenProvider;
import me.bk.community.member.application.MemberService;
import me.bk.community.member.domain.Member;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberService memberService;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public LoginTokenResponse loginToken(LoginTokenRequest loginTokenRequest) {
		Member findMember = memberService.findByEmail(loginTokenRequest.getEmail());
		findMember.checkPassword(loginTokenRequest.getPassword());
		String token = jwtTokenProvider.createToken(findMember.getEmail());
		return new LoginTokenResponse(token);
	}
}
