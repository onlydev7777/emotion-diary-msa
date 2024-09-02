package com.auth.authservice.security.dto;


import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.Member;
import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.domain.embeddable.Email;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@AllArgsConstructor
public class MemberDetails implements UserDetails {

  private Long id;
  private String userId;
  private String password;
  private Email email;
  private SocialType socialType;
//  private Role role;

  @Override
  public String getUsername() {
    return this.userId;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }


  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public static MemberDetails of(Member member) {
    return new MemberDetails(member.getId(), member.getUserId(), member.getPassword().getPassword(), member.getEmail(), member.getSocialType());
  }

  public static MemberDetails of(MemberDto memberDto) {
    return new MemberDetails(memberDto.getId(), memberDto.getUserId(), memberDto.getPassword().getPassword(), memberDto.getEmail(),
        memberDto.getSocialType());
  }
}
