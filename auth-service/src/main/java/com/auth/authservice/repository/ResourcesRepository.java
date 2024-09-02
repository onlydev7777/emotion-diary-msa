package com.auth.authservice.repository;

import com.auth.authservice.domain.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourcesRepository extends JpaRepository<Resources, Long> {

}
