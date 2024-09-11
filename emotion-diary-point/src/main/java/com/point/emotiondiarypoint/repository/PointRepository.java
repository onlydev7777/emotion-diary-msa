package com.point.emotiondiarypoint.repository;

import com.point.emotiondiarypoint.domain.Point;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

  Optional<Point> findByMemberId(Long memberId);
}
