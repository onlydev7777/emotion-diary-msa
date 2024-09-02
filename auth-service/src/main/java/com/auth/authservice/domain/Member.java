package com.auth.authservice.domain;

import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.embeddable.Email;
import com.auth.authservice.domain.embeddable.Password;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private String userId;

  @Embedded
  private Password password;

  @Column(name = "name", nullable = false)
  private String name;

  @Embedded
  private Email email;

  @Enumerated(EnumType.STRING)
  private SocialType socialType;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<MemberRole> memberRoles = new ArrayList<>();

  @Builder
  public Member(String userId, Password password, String name, Email email, SocialType socialType) {
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
    this.socialType = socialType;
  }

  public static Member of(String userId, Password password, String name, Email email, SocialType socialType) {
    return Member.builder()
        .userId(userId)
        .password(password)
        .name(name)
        .email(email)
        .socialType(socialType)
        .build();
  }
}
