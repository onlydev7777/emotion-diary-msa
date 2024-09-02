package com.auth.authservice;

import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.Member;
import com.auth.authservice.domain.embeddable.Email;
import com.auth.authservice.domain.embeddable.Password;
import com.auth.authservice.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitData {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {
    Member savedMember = memberRepository.save(Member.of(
        "test",
        new Password("qwer1234!", passwordEncoder),
        "테스트",
        new Email("test@test.com"),
        SocialType.NONE)
    );

  }
}
