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

import me.bk.community.member.application.MemberService;
import me.bk.community.member.domain.Member;
import me.bk.community.member.dto.MemberCreateRequest;

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
}
