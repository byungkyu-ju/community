package me.bk.community.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LoginTokenResponse {
	private String accessToken;
}
