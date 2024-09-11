package com.emotion.emotiondiarydiary.service;

import com.emotion.emotiondiarydiary.domain.Diary;
import com.emotion.emotiondiarydiary.dto.DiaryDto;
import com.emotion.emotiondiarydiary.mapper.DiaryMapper;
import com.emotion.emotiondiarydiary.repository.DiaryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryService {

  private final DiaryRepository repository;
  private final DiaryMapper mapper;

  @Transactional
  public DiaryDto save(DiaryDto dto) {
    Diary savedDiary = repository.save(mapper.toEntity(dto));

    return mapper.toDto(savedDiary);
  }

  public DiaryDto findById(Long diaryId) {
    Diary diary = repository.findById(diaryId)
        .orElseThrow();

    return mapper.toDto(diary);
  }

  public List<DiaryDto> findDiariesByMonth(Long memberId, String diaryYearMonth) {
    return repository.findAllByMemberIdAndDiaryYearMonth(memberId, diaryYearMonth).stream()
        .map(mapper::toDto)
        .toList();
  }

  @Transactional
  public DiaryDto update(DiaryDto dto) {
    Diary findDiary = repository.findById(dto.getId())
        .orElseThrow();

    findDiary.update(dto.getSubject(), dto.getContent(), dto.getEmotionStatus(), dto.getDiaryDate());

    return mapper.toDto(findDiary);
  }

  @Transactional
  public void deleteById(Long diaryId) {
    Diary diary = repository.findById(diaryId)
        .orElseThrow();
    repository.delete(diary);
  }
}
