package me.bk.community.member;

import static me.bk.community.auth.AuthAcceptanceTest.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import me.bk.community.AcceptanceTest;
import me.bk.community.auth.dto.LoginTokenResponse;
import me.bk.community.member.dto.MemberCreateRequest;
import me.bk.community.member.dto.MemberResponse;
import me.bk.community.member.dto.UpdateMemberRequest;

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

	@DisplayName("사용자는 자신의 정보를 관리할 수 있다.")
	@Test
	void 사용자는_자신의_정보를_관리할_수_있다(){
		// given
		ExtractableResponse<Response> createResponse = 회원_생성(EMAIL, PASSWORD, PASSWORD_CONFIRM, NICK_NAME);
		회원이_등록됨(createResponse);
		ExtractableResponse<Response> loginTokenRequest = 로그인_요청(EMAIL, PASSWORD);
		LoginTokenResponse loginTokenResponse = loginTokenRequest.as(LoginTokenResponse.class);

		// when
		ExtractableResponse<Response> findResponse = 자신의_정보를_조회한다(loginTokenResponse);

		// then
		회원정보가_조회된다(findResponse);

		// when
		UpdateMemberRequest updateRequest = new UpdateMemberRequest("updateNickName");
		ExtractableResponse<Response> updateResponse = 자신의_정보를_수정한다(loginTokenResponse, updateRequest);

		// then
		회원정보가_수정된다(updateResponse);

		// when
		ExtractableResponse<Response> leaveMemberResponse = 자신의_계정을_탈퇴한다(loginTokenResponse);

		// then
		회원정보가_삭제된다(leaveMemberResponse);
	}

	private void 회원정보가_삭제된다(ExtractableResponse<Response> leaveMemberResponse) {
		assertThat(leaveMemberResponse.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	private ExtractableResponse<Response> 자신의_계정을_탈퇴한다(LoginTokenResponse loginTokenResponse) {
		return RestAssured
			.given().log().all()
			.auth().oauth2(loginTokenResponse.getAccessToken())
			.when().delete("/members/me")
			.then().log().all()
			.extract();
	}

	private void 회원정보가_수정된다(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	private ExtractableResponse<Response> 자신의_정보를_수정한다(LoginTokenResponse loginTokenResponse,
		UpdateMemberRequest updateRequest) {

		return RestAssured
			.given().log().all()
			.auth().oauth2(loginTokenResponse.getAccessToken())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.body(updateRequest)
			.when().put("/members/me")
			.then().log().all()
			.extract();
	}

	private void 회원정보가_조회된다(ExtractableResponse<Response> response) {
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		MemberResponse memberResponse = response.as(MemberResponse.class);
		assertThat(memberResponse.getId()).isNotNull();
	}

	public static ExtractableResponse<Response> 자신의_정보를_조회한다(LoginTokenResponse loginTokenResponse) {
		return RestAssured
			.given().log().all()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.auth().oauth2(loginTokenResponse.getAccessToken())
			.when().get("/members/me")
			.then().log().all()
			.extract();
	}

	public static ExtractableResponse<Response> 회원_생성(String email, String password,String passwordConfirm, String nickName) {
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
