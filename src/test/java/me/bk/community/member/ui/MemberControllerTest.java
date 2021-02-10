package me.bk.community.member.ui;

import static me.bk.community.member.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import me.bk.community.config.security.dto.LoginMember;
import me.bk.community.member.application.MemberService;
import me.bk.community.member.domain.Member;
import me.bk.community.member.dto.MemberCreateRequest;
import me.bk.community.member.dto.MemberResponse;
import me.bk.community.member.dto.UpdateMemberRequest;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {
	@Mock
	private MemberService memberService;

	private MemberController memberController;

	@BeforeEach
	void setUp() {
		this.memberController = new MemberController(memberService);
	}

	@DisplayName("회원 생성")
	@Test
	void createMember() {
		// given
		MemberCreateRequest request = MemberCreateRequest.builder()
			.email(EMAIL)
			.password(PASSWORD)
			.passwordConfirm(PASSWORD_CONFIRM)
			.nickName(NICK_NAME)
			.build();

		// when
		when(memberService.createMember(request)).thenReturn(new Member(EMAIL, PASSWORD, NICK_NAME));
		ResponseEntity response = memberController.createMember(request);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@DisplayName("자신의 정보 조회")
	@Test
	void findMemberOfMine() {
		// given
		LoginMember loginMember = LoginMember.builder()
			.id(1L)
			.email(EMAIL)
			.nickName(NICK_NAME)
			.build();

		// when
		when(memberService.findMember(any())).thenReturn(new MemberResponse(1L, EMAIL, NICK_NAME));
		ResponseEntity response = memberController.findMemberOfMine(loginMember);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("자신의 정보 수정")
	@Test
	void updateMemberOfMine() {
		// given
		LoginMember loginMember = LoginMember.builder()
			.id(1L)
			.email(EMAIL)
			.nickName(NICK_NAME)
			.build();

		UpdateMemberRequest updateMemberRequest = UpdateMemberRequest.builder()
			.nickName("updated")
			.build();

		// when
		ResponseEntity response = memberController.updateMemberOfMine(loginMember, updateMemberRequest);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@DisplayName("자신의 정보 삭제")
	@Test
	void deleteMemberOfMine() {
		// given
		LoginMember loginMember = LoginMember.builder()
			.id(1L)
			.email(EMAIL)
			.nickName(NICK_NAME)
			.build();

		// when
		ResponseEntity response = memberController.deleteMemberOfMine(loginMember);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}


}
