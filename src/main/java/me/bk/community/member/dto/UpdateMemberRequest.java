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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateMemberRequest {
	private String nickName;

	public Member toMember() {
		return new Member(nickName);
	}
}
