package me.bk.community.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import me.bk.community.member.domain.Member;
import me.bk.community.member.dto.MemberCreateRequest;
import me.bk.community.member.repository.MemberRepository;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	@Transactional
	public Member createMember(MemberCreateRequest memberCreateRequest) {
		validateCreateMember(memberCreateRequest);
		return memberRepository.save(memberCreateRequestToMember(memberCreateRequest));
	}

	private Member memberCreateRequestToMember(MemberCreateRequest memberCreateRequest) {
		Member member = Member.builder()
			.email(memberCreateRequest.getEmail())
			.password(memberCreateRequest.getPassword())
			.nickName(memberCreateRequest.getNickName())
			.build();

		validateMemberPassword(member, memberCreateRequest.getPasswordConfirm());
		return member;
	}

	private void validateMemberPassword(Member member, String passwordConfirm) {
		if (!member.isEqualPassword(passwordConfirm)) {
			throw new IllegalArgumentException("입력한 비밀번호가 일치하지 않음");
		}
	}

	private void validateCreateMember(MemberCreateRequest memberCreateRequest) {
		Member findMember = findByEmail(memberCreateRequest.getEmail());
		if (findMember != null) {
			throw new IllegalArgumentException("이메일 중복");
		}
	}

	private Member findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}
}
