package com.point.emotiondiarypoint.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class PointHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "point_id")
  private Point point;

  @Column(name = "score")
  private int score;

  @Column(name = "details")
  private String details;

  public void setPoint(Point point) {
    this.point = point;
    point.addPointHistory(this);
  }

  @Builder
  public PointHistory(Point point, int score, String details) {
    this.score = score;
    this.details = details;
    setPoint(point);
  }

  public static PointHistory of(Point point, int score, String details) {
    return PointHistory.builder()
        .point(point)
        .score(score)
        .details(details)
        .build();
  }
}
