package me.bk.community.auth.ui;

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

import me.bk.community.auth.application.AuthService;
import me.bk.community.auth.dto.LoginTokenRequest;
import me.bk.community.auth.dto.LoginTokenResponse;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
	@Mock
	private AuthService authService;
	private AuthController authController;

	@BeforeEach
	void setUp() {
		this.authController = new AuthController(authService);
	}

	@DisplayName("로그인 요청")
	@Test
	void login(){
		// given
		LoginTokenRequest loginTokenRequest = LoginTokenRequest.builder()
			.email(EMAIL)
			.password(PASSWORD)
			.build();

		// when
		when(authService.loginToken(loginTokenRequest)).thenReturn(new LoginTokenResponse("dummyToken"));
		ResponseEntity response = authController.loginToken(loginTokenRequest);

		// then
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
