package me.bk.community.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.bk.community.member.domain.Member;

/**
 * @author : byungkyu
 * @date : 2021/02/10
 * @description :
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberResponse {
	private Long id;
	private String email;
	private String nickName;

	public static MemberResponse of(Member member) {
		return new MemberResponse(member.getId(), member.getEmail(), member.getNickName());
	}
}
