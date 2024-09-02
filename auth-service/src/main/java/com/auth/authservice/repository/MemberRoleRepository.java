package com.auth.authservice.repository;


import com.auth.authservice.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

}
