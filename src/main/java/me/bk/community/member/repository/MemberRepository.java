package me.bk.community.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.bk.community.member.domain.Member;

/**
 * @author : byungkyu
 * @date : 2021/02/08
 * @description :
 **/
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findByEmail(String email);
}
