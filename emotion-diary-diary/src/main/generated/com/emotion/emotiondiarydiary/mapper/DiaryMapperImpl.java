package com.emotion.emotiondiarydiary.mapper;

import com.emotion.emotiondiarydiary.api.request.DiaryRequest;
import com.emotion.emotiondiarydiary.api.request.DiaryUpdateRequest;
import com.emotion.emotiondiarydiary.api.response.DiaryResponse;
import com.emotion.emotiondiarydiary.api.response.MemberResponse;
import com.emotion.emotiondiarydiary.domain.Diary;
import com.emotion.emotiondiarydiary.dto.DiaryDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-06T17:34:54+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class DiaryMapperImpl implements DiaryMapper {

    @Override
    public DiaryDto toDto(Diary diary) {
        if ( diary == null ) {
            return null;
        }

        DiaryDto.DiaryDtoBuilder diaryDto = DiaryDto.builder();

        diaryDto.id( diary.getId() );
        diaryDto.subject( diary.getSubject() );
        diaryDto.content( diary.getContent() );
        diaryDto.emotionStatus( diary.getEmotionStatus() );
        diaryDto.diaryDate( diary.getDiaryDate() );
        diaryDto.diaryYearMonth( diary.getDiaryYearMonth() );
        diaryDto.memberId( diary.getMemberId() );

        return diaryDto.build();
    }

    @Override
    public Diary toEntity(DiaryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Diary.DiaryBuilder diary = Diary.builder();

        diary.subject( dto.getSubject() );
        diary.content( dto.getContent() );
        diary.emotionStatus( dto.getEmotionStatus() );
        diary.diaryDate( dto.getDiaryDate() );
        diary.memberId( dto.getMemberId() );

        return diary.build();
    }

    @Override
    public DiaryResponse toResponse(DiaryDto dto, MemberResponse memberResponse) {
        if ( dto == null ) {
            return null;
        }

        DiaryResponse.DiaryResponseBuilder diaryResponse = DiaryResponse.builder();

        diaryResponse.id( dto.getId() );
        diaryResponse.subject( dto.getSubject() );
        diaryResponse.content( dto.getContent() );
        diaryResponse.emotionStatus( dto.getEmotionStatus() );
        diaryResponse.diaryDate( dto.getDiaryDate() );
        diaryResponse.diaryYearMonth( dto.getDiaryYearMonth() );

        return diaryResponse.build();
    }

    @Override
    public DiaryResponse toResponse(DiaryDto dto) {
        if ( dto == null ) {
            return null;
        }

        DiaryResponse.DiaryResponseBuilder diaryResponse = DiaryResponse.builder();

        diaryResponse.id( dto.getId() );
        diaryResponse.subject( dto.getSubject() );
        diaryResponse.content( dto.getContent() );
        diaryResponse.emotionStatus( dto.getEmotionStatus() );
        diaryResponse.diaryDate( dto.getDiaryDate() );
        diaryResponse.diaryYearMonth( dto.getDiaryYearMonth() );

        return diaryResponse.build();
    }

    @Override
    public DiaryDto toDto(DiaryRequest request) {
        if ( request == null ) {
            return null;
        }

        DiaryDto.DiaryDtoBuilder diaryDto = DiaryDto.builder();

        diaryDto.subject( request.getSubject() );
        diaryDto.content( request.getContent() );
        diaryDto.emotionStatus( request.getEmotionStatus() );
        diaryDto.diaryDate( request.getDiaryDate() );
        diaryDto.memberId( request.getMemberId() );

        diaryDto.diaryYearMonth( toDiaryYearMonth(request.getDiaryDate()) );

        return diaryDto.build();
    }

    @Override
    public DiaryDto toDto(DiaryUpdateRequest request) {
        if ( request == null ) {
            return null;
        }

        DiaryDto.DiaryDtoBuilder diaryDto = DiaryDto.builder();

        diaryDto.id( request.getId() );
        diaryDto.subject( request.getSubject() );
        diaryDto.content( request.getContent() );
        diaryDto.emotionStatus( request.getEmotionStatus() );
        diaryDto.diaryDate( request.getDiaryDate() );
        diaryDto.memberId( request.getMemberId() );

        diaryDto.diaryYearMonth( toDiaryYearMonth(request.getDiaryDate()) );

        return diaryDto.build();
    }
}
