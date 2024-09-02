package com.auth.authservice.service;

import com.auth.authservice.constant.SocialType;
import com.auth.authservice.domain.Member;
import com.auth.authservice.domain.MemberRole;
import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.mapper.MemberMapper;
import com.auth.authservice.repository.MemberRepository;
import com.auth.authservice.repository.MemberRoleRepository;
import com.auth.authservice.repository.RoleRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

  private final MemberRepository repository;
  private final RoleRepository roleRepository;
  private final MemberRoleRepository memberRoleRepository;
  private final MemberMapper mapper;

  @Transactional
  public MemberDto save(MemberDto dto) {
    Member savedMember = repository.save(mapper.toEntity(dto));

    if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
      roleRepository.findAllById(dto.getRoleIds()).forEach(
          role -> memberRoleRepository.save(MemberRole.of(savedMember, role))
      );
    }

    return mapper.toDto(savedMember, dto.getRoleIds());
  }

  public MemberDto findById(Long memberId) {
    Member member = repository.findById(memberId)
        .orElseThrow();

    return mapper.toDto(member, member.getMemberRoles().stream()
        .map(mr -> mr.getRole().getId())
        .collect(Collectors.toSet()));
  }

  @Transactional
  public void deleteById(Long memberId) {
    Member member = repository.findById(memberId)
        .orElseThrow();
    repository.delete(member);
  }

  public Optional<MemberDto> findByUserIdAndSocialType(String userId, SocialType socialType) {
    Optional<Member> findMember = repository.findByUserIdAndSocialType(userId, socialType);
    if (findMember.isEmpty()) {
      return Optional.empty();
    }

    Member member = findMember.get();

    return Optional.of(
        mapper.toDto(member, member.getMemberRoles().stream()
            .map(mr -> mr.getRole().getId())
            .collect(Collectors.toSet()))
    );
  }
}
