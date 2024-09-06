package com.emotion.emotiondiarydiary.domain;

import com.emotion.emotiondiarydiary.constant.EmotionStatus;
import com.emotion.emotiondiarydiary.util.DateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "diary")
@Entity
public class Diary {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "subject", length = 300)
  private String subject;
  @Column(name = "content", nullable = false, length = 5000)
  private String content;

  @Enumerated(EnumType.STRING)
  @Column(name = "emotion_status", nullable = false)
  private EmotionStatus emotionStatus;

  @Column(name = "diary_date", nullable = false)
  private LocalDate diaryDate;

  @Column(name = "diary_year_month", nullable = false, length = 6)
  private String diaryYearMonth;

  private Long memberId;

  public void update(String subject, String content, EmotionStatus emotionStatus, LocalDate diaryDate) {
    this.subject = subject;
    this.content = content;
    this.emotionStatus = emotionStatus;
    this.diaryDate = diaryDate;
    this.diaryYearMonth = DateUtil.getYearMonth(diaryDate);
  }

  @Builder
  public Diary(String subject, String content, EmotionStatus emotionStatus, LocalDate diaryDate, Long memberId) {
    this.subject = subject;
    this.content = content;
    this.emotionStatus = emotionStatus;
    this.diaryDate = diaryDate;
    this.diaryYearMonth = DateUtil.getYearMonth(diaryDate);
    this.memberId = memberId;
  }

  public static Diary of(String subject, String content, EmotionStatus emotionStatus, LocalDate diaryDate, Long memberId) {
    return Diary.builder()
        .subject(subject)
        .content(content)
        .emotionStatus(emotionStatus)
        .diaryDate(diaryDate)
        .memberId(memberId)
        .build();
  }
}
