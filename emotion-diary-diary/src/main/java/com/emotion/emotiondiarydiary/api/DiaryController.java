package com.emotion.emotiondiarydiary.api;

import com.emotion.emotiondiarydiary.api.request.DiaryRequest;
import com.emotion.emotiondiarydiary.api.request.DiaryUpdateRequest;
import com.emotion.emotiondiarydiary.api.response.DeleteResponse;
import com.emotion.emotiondiarydiary.api.response.DiaryListResponse;
import com.emotion.emotiondiarydiary.api.response.DiaryResponse;
import com.emotion.emotiondiarydiary.api.response.MemberResponse;
import com.emotion.emotiondiarydiary.api.response.PointResponse;
import com.emotion.emotiondiarydiary.dto.DiaryDto;
import com.emotion.emotiondiarydiary.dto.PointHistoryRequest;
import com.emotion.emotiondiarydiary.error.ApiResult;
import com.emotion.emotiondiarydiary.mapper.DiaryMapper;
import com.emotion.emotiondiarydiary.security.jwt.Payload;
import com.emotion.emotiondiarydiary.security.util.TokenUtil;
import com.emotion.emotiondiarydiary.service.DiaryService;
import com.emotion.emotiondiarydiary.service.PointKafkaProducer;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DiaryController {

  private final Environment env;
  private final DiaryService service;
  private final DiaryMapper mapper;
  private final AuthServiceClient authServiceClient;
  private final PointServiceClient pointServiceClient;
  private final CircuitBreakerFactory circuitBreakerFactory;
  private final PointKafkaProducer kafkaProducer;

  @GetMapping("/test")
  public String test() {
    System.out.println("env.getProperty(\"jwt.secret-key\") = " + env.getProperty("jwt.secret-key"));
    System.out.println("endpoints = " + env.getProperty("management.endpoints.web.exposure.include"));
    return "gogogo";
  }

  @GetMapping("/{diaryId}")
  public ResponseEntity<ApiResult<DiaryResponse>> findDiary(Long diaryId) {
    DiaryDto diaryDto = service.findById(diaryId);
//    MemberResponse memberResponse = authServiceClient.getMember(diaryDto.getMemberId()).getResponse();

    CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
    MemberResponse memberResponse = circuitbreaker.run(
        () -> authServiceClient.getMember(diaryDto.getMemberId()).getResponse(),
        throwable -> new MemberResponse()
    );

    DiaryResponse diaryResponse = mapper.toResponse(diaryDto, memberResponse);
    return ResponseEntity.ok(ApiResult.OK(diaryResponse));
  }

  @PostMapping
  public ResponseEntity<ApiResult<DiaryResponse>> saveDiary(@RequestBody DiaryRequest request) {
    DiaryResponse savedDiaryResponse = mapper.toResponse(service.save(mapper.toDto(request)));
    PointHistoryRequest pointHistoryRequest = PointHistoryRequest.builder()
        .memberId(request.getMemberId())
        .score(10)
        .details("일기쓰기")
        .build();

    log.info("kafkaProducer.send 호출 : memberId : {}", pointHistoryRequest.getMemberId());
    kafkaProducer.send(pointHistoryRequest);
    return ResponseEntity.ok(ApiResult.OK(savedDiaryResponse));
  }

  @PatchMapping
  public ResponseEntity<ApiResult<DiaryResponse>> updateDiary(@RequestBody DiaryUpdateRequest request) {
    DiaryResponse savedDiaryResponse = mapper.toResponse(service.update(mapper.toDto(request)));
    return ResponseEntity.ok(ApiResult.OK(savedDiaryResponse));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResult<DeleteResponse>> deleteDiary(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.ok(ApiResult.OK(new DeleteResponse(id)));
  }

  @GetMapping("/{memberId}/month-list")
  public ResponseEntity<ApiResult<DiaryListResponse>> findDiariesByMonth(@PathVariable Long memberId, @RequestParam String diaryYearMonth,
      HttpServletRequest request) {
    log.debug("start /diary/{memberId}/month-list");
    List<DiaryResponse> diaryResponseList = service.findDiariesByMonth(memberId, diaryYearMonth).stream()
        .map(mapper::toResponse)
        .toList();

//    MemberResponse memberResponse = authServiceClient.getMember(memberId).getResponse();

//    log.info("Authorization Token : {}", request.getHeader(HttpHeaders.AUTHORIZATION));
    log.info("Authorization Token : {}", TokenUtil.getToken());
    CircuitBreaker circuitbreaker = circuitBreakerFactory.create("circuitbreaker");
    MemberResponse memberResponse = circuitbreaker.run(
        () -> {
          log.info("Authorization Async Token : {}", TokenUtil.getToken());
          return authServiceClient.getMember(memberId).getResponse();
        },
        throwable -> {
          throwable.printStackTrace();
          return new MemberResponse();
        }
    );

    PointResponse pointResponse = circuitbreaker.run(
        () -> pointServiceClient.getPointByMemberId(memberId).getResponse(),
        throwable -> {
          throwable.printStackTrace();
          return new PointResponse();
        }
    );

    memberResponse.setPointResponse(pointResponse);
    Payload payload = TokenUtil.getPayload();
    log.debug(payload.toString());
    log.debug("end /diary/{memberId}/month-list");

    return ResponseEntity.ok(ApiResult.OK(
        new DiaryListResponse(diaryResponseList, memberResponse)
    ));
  }
}
