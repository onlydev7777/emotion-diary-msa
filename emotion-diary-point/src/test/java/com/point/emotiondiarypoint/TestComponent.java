package com.point.emotiondiarypoint;

import com.point.emotiondiarypoint.domain.Point;
import com.point.emotiondiarypoint.domain.PointHistory;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class TestComponent {

  private Point point;
  private PointHistory pointHistory1;
  private PointHistory pointHistory2;
  private PointHistory pointHistory3;

  public void defaultPointSetUp() {
    point = Point.of(1L);
    pointHistory1 = PointHistory.of(point, 10, "일기쓰기");
    pointHistory2 = PointHistory.of(point, 10, "일기쓰기");
    pointHistory3 = PointHistory.of(point, 20, "일기공유");
  }
}
