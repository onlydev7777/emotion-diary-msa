package com.emotion.emotiondiarydiary.repository;

import com.emotion.emotiondiarydiary.domain.Diary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

  List<Diary> findAllByMemberIdAndDiaryYearMonth(Long memberId, String diaryYearMonth);
}
