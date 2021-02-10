package me.bk.community.config.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : byungkyu
 * @date : 2021/02/10
 * @description :
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginMember {
	private Long id;
	private String email;
	private String nickName;
}
