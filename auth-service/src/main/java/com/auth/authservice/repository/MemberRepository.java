package com.auth.authservice.repository;

import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByUserIdAndSocialType(String userId, SocialType socialType);
}
