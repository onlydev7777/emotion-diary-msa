package com.point.emotiondiarypoint.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.point.emotiondiarypoint.repository.PointHistoryRepository;
import com.point.emotiondiarypoint.repository.PointRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class PointTest {

  @Autowired
  private EntityManager em;
  @Autowired
  private PointRepository repository;

  @Autowired
  private PointHistoryRepository historyRepository;

  @Test
  void 포인트_생성() {
    //given
    Point point = Point.of(1L);
    repository.save(point);

    PointHistory 일기쓰기1 = PointHistory.of(point, 10, "일기쓰기");
    PointHistory 일기쓰기2 = PointHistory.of(point, 10, "일기쓰기");
    PointHistory 일기공유1 = PointHistory.of(point, 20, "일기공유");

    List<PointHistory> histories = List.of(일기쓰기1, 일기쓰기2, 일기공유1);
    historyRepository.saveAll(histories);

    em.flush();
    em.clear();
    Point savedPoint = repository.findById(point.getId()).orElseThrow();

    //when
    List<PointHistory> savedPointHistory = historyRepository.findAllByPoint(point);

    //then
    assertThat(savedPointHistory)
        .allMatch(pointHistory -> pointHistory.getPoint().equals(savedPoint));

    assertThat(savedPoint.getScore()).isEqualTo(40);
  }
}
