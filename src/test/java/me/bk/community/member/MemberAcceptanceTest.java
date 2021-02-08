package me.bk.community.member;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import me.bk.community.AcceptanceTest;
import me.bk.community.member.dto.MemberCreateRequest;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
public class MemberAcceptanceTest extends AcceptanceTest {
	public static final String EMAIL = "test@gmail.com";
	public static final String PASSWORD = "password";
	public static final String PASSWORD_CONFIRM = "password";
	public static final String NICK_NAME = "tester";

	@DisplayName("사용자는 회원가입을 할 수 있다.")
	@Test
	void 사용자는_회원가입을_할_수_있다() {
		// when
		ExtractableResponse<Response> createResponse = 회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);

		// then
		회원이_등록됨(createResponse);

	}

	private ExtractableResponse<Response> 회원_생성(String email, String password,String passwordConfirm, String nickName) {
		MemberCreateRequest request = MemberCreateRequest.builder()
			.email(email)
			.password(password)
			.passwordConfirm(passwordConfirm)
			.nickName(nickName)
			.build();

		return RestAssured
			.given().log().all()
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(request)
			.when().post("/members")
			.then().log().all()
			.extract();
	}


	private void 회원이_등록됨(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
	}
}
