package me.bk.community.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class LoginTokenRequest {
	private String email;
	private String password;
}
