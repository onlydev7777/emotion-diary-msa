package com.emotion.emotiondiarydiary.mapper;


import com.emotion.emotiondiarydiary.api.request.DiaryRequest;
import com.emotion.emotiondiarydiary.api.request.DiaryUpdateRequest;
import com.emotion.emotiondiarydiary.api.response.DiaryResponse;
import com.emotion.emotiondiarydiary.api.response.MemberResponse;
import com.emotion.emotiondiarydiary.domain.Diary;
import com.emotion.emotiondiarydiary.dto.DiaryDto;
import com.emotion.emotiondiarydiary.util.DateUtil;
import java.time.LocalDate;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DiaryMapper {

  DiaryDto toDto(Diary diary);

  Diary toEntity(DiaryDto dto);

  //  @Mapping(target = "memberResponse", expression = "java(memberResponse)")
  DiaryResponse toResponse(DiaryDto dto, @Context MemberResponse memberResponse);

  DiaryResponse toResponse(DiaryDto dto);

  @Mapping(target = "diaryYearMonth", expression = "java(toDiaryYearMonth(request.getDiaryDate()))")
  DiaryDto toDto(DiaryRequest request);

  @Mapping(target = "diaryYearMonth", expression = "java(toDiaryYearMonth(request.getDiaryDate()))")
  DiaryDto toDto(DiaryUpdateRequest request);

  default String toDiaryYearMonth(LocalDate diaryDate) {
    return DateUtil.getYearMonth(diaryDate);
  }
}
