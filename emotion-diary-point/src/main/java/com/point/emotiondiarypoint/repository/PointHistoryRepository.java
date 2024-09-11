package com.point.emotiondiarypoint.repository;

import com.point.emotiondiarypoint.domain.Point;
import com.point.emotiondiarypoint.domain.PointHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

  List<PointHistory> findAllByPoint(Point point);
}
