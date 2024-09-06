package com.auth.authservice.mapper;

import com.auth.authservice.domain.Member;
import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.domain.embeddable.Email;
import com.auth.authservice.domain.embeddable.Password;
import com.auth.authservice.service.dto.MemberJoinRequest;
import com.auth.authservice.service.dto.MemberResponse;
import java.util.Set;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  //  @Mapping(target = "password", expression = "java(toPassword(dto.getPassword(), passwordEncoder))")
//  @Mapping(target = "email", expression = "java(toEmail(dto.getEmail()))")
  Member toEntity(MemberDto dto);

  //    @Mapping(target = "password", ignore = true)
//  @Mapping(target = "email", expression = "java(member.getEmail().getEmail())")
  @Mapping(target = "roleIds", expression = "java(roleIds)")
  MemberDto toDto(Member member, @Context Set<Long> roleIds);

  default Password toPassword(String password, PasswordEncoder passwordEncoder) {
    return new Password(password, passwordEncoder);
  }

  default Email toEmail(String email) {
    return new Email(email);
  }


  @Mapping(target = "email", expression = "java(memberDto.getEmail().getEmail())")
  MemberResponse toResponse(MemberDto memberDto);

  @Mapping(target = "password", expression = "java(toPassword(request.getPassword(), passwordEncoder))")
  @Mapping(target = "email", expression = "java(toEmail(request.getEmail()))")
  MemberDto toDto(MemberJoinRequest request, @Context PasswordEncoder passwordEncoder);
}
