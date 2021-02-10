package me.bk.community.auth;

import static me.bk.community.member.MemberAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import me.bk.community.AcceptanceTest;
import me.bk.community.auth.dto.LoginTokenRequest;
import me.bk.community.auth.dto.LoginTokenResponse;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
public class AuthAcceptanceTest extends AcceptanceTest {

	@DisplayName("사용자는 로그인을 할 수 있다.")
	@Test
	void 사용자는_로그인을_할_수_있다(){
		// given
		회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);

		// when
		ExtractableResponse<Response> loginResponse = 로그인_요청(EMAIL, PASSWORD);

		// then
		로그인_됨(loginResponse);
	}

	@DisplayName("존재하지 않는 사용자 로그인 실패")
	@Test
	void 존재하지_않는_사용자_로그인_실패(){
		// when
		ExtractableResponse<Response> loginResponse = 로그인_요청(EMAIL, PASSWORD);

		// then
		로그인_실패(loginResponse);
	}


	@DisplayName("로그인이 실패한다(비밀번호 불일치)")
	@Test
	void 로그인이_실패한다_비밀번호_불일치(){
		// given
		회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);

		// when
		ExtractableResponse<Response> loginResponse = 로그인_요청(EMAIL, "invalidPassword");

		// then
		로그인실패_비밀번호_불일치(loginResponse);
	}


	@DisplayName("변조된 토큰은 로그인을 할 수 없다")
	@Test
	void 변조된_토큰은_로그인을_할_수_없다(){
		// given
		LoginTokenResponse invalidToken = new LoginTokenResponse("invalidToken");

		// when
		ExtractableResponse<Response> findResponse = 자신의_정보를_조회한다(invalidToken);

		// then
		로그인실패_유효하지_않은_토큰(findResponse);
	}

	private void 로그인실패_유효하지_않은_토큰(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	private void 로그인실패_비밀번호_불일치(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	private void 로그인_실패(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	private void 로그인_됨(ExtractableResponse<Response> response) {
		LoginTokenResponse loginTokenResponse = response.as(LoginTokenResponse.class);
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(loginTokenResponse.getAccessToken()).isNotEmpty();
	}

	public static ExtractableResponse<Response> 로그인_요청(String email, String password) {
		LoginTokenRequest loginTokenRequest = new LoginTokenRequest(email, password);
		return RestAssured
			.given().log().all()
			.body(loginTokenRequest)
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.when().post("/login/token")
			.then().log().all().extract();
	}
}
