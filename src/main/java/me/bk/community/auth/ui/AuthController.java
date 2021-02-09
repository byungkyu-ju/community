package me.bk.community.auth.ui;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.bk.community.auth.application.AuthService;
import me.bk.community.auth.dto.LoginTokenRequest;
import me.bk.community.auth.dto.LoginTokenResponse;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/login/token")
	public ResponseEntity loginToken(@RequestBody LoginTokenRequest loginTokenRequest) {
		LoginTokenResponse loginTokenResponse = authService.loginToken(loginTokenRequest);
		return ResponseEntity.ok(loginTokenResponse);
	}
}
