package me.bk.community.member.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.bk.community.config.security.dto.LoginMember;
import me.bk.community.config.security.user.AuthenticationPrincipal;
import me.bk.community.member.application.MemberService;
import me.bk.community.member.domain.Member;
import me.bk.community.member.dto.MemberCreateRequest;
import me.bk.community.member.dto.MemberResponse;
import me.bk.community.member.dto.UpdateMemberRequest;

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

	@GetMapping("/me")
	public ResponseEntity findMemberOfMine(@AuthenticationPrincipal LoginMember loginMember) {
		MemberResponse response = memberService.findMember(loginMember.getId());
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/me")
	public ResponseEntity updateMemberOfMine(@AuthenticationPrincipal LoginMember loginMember, UpdateMemberRequest request) {
		memberService.updateMember(loginMember.getId(), request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/me")
	public ResponseEntity<MemberResponse> deleteMemberOfMine(@AuthenticationPrincipal LoginMember loginMember) {
		memberService.deleteMember(loginMember.getId());
		return ResponseEntity.noContent().build();
	}
}
