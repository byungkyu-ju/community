package me.bk.community.member.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.bk.community.member.application.MemberService;
import me.bk.community.member.domain.Member;
import me.bk.community.member.dto.MemberCreateRequest;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
		Member createdMember = memberService.createMember(memberCreateRequest);
		return ResponseEntity.created(URI.create("/members/" + createdMember.getId())).build();
	}
}
