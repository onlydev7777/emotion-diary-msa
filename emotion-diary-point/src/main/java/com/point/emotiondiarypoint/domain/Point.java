package com.point.emotiondiarypoint.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table
@Entity
public class Point {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "member_id")
  private Long memberId;

  @Column(name = "score")
  private int score;

  @OneToMany(mappedBy = "point", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PointHistory> pointHistories = new ArrayList<>();

  public void addPointHistory(PointHistory pointHistory) {
    this.pointHistories.add(pointHistory);
    this.score += pointHistory.getScore();
  }

  @Builder
  public Point(Long memberId, List<PointHistory> pointHistories) {
    this.memberId = memberId;
    this.score = 0;
    if (pointHistories == null) {
      pointHistories = new ArrayList<>();
    }
    this.pointHistories = pointHistories;
  }

  public static Point of(Long memberId) {
    return Point.builder()
        .memberId(memberId)
        .build();
  }
}
