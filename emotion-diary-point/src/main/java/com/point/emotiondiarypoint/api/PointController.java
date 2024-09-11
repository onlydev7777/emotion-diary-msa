package com.point.emotiondiarypoint.api;

import com.point.emotiondiarypoint.api.request.PointHistoryRequest;
import com.point.emotiondiarypoint.api.request.PointRequest;
import com.point.emotiondiarypoint.api.response.PointResponse;
import com.point.emotiondiarypoint.dto.PointDto;
import com.point.emotiondiarypoint.dto.PointHistoryDto;
import com.point.emotiondiarypoint.error.ApiResult;
import com.point.emotiondiarypoint.mapper.PointHistoryMapper;
import com.point.emotiondiarypoint.mapper.PointMapper;
import com.point.emotiondiarypoint.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PointController {

  private final PointService service;
  private final PointMapper mapper;
  private final PointHistoryMapper pointHistoryMapper;

  @PostMapping
  public ResponseEntity<ApiResult<PointDto>> save(PointRequest request) {
    PointDto pointDto = service.save(mapper.toDto(request));
    return ResponseEntity.ok(ApiResult.OK(pointDto));
  }

  @PostMapping("/add")
  public ResponseEntity<ApiResult<PointHistoryDto>> add(PointHistoryRequest request) {
    PointHistoryDto pointHistoryDto = service.add(pointHistoryMapper.toDto(request));
    return ResponseEntity.ok(ApiResult.OK(pointHistoryDto));
  }

  @GetMapping("/{memberId}")
  public ResponseEntity<ApiResult<PointResponse>> get(@PathVariable Long memberId) {
    System.out.println("PointController.get");
    log.info("start PointController.get memberId {}", memberId);

    PointResponse response = mapper.toResponse(service.getOrSave(memberId));

    log.info("end PointController.get memberId {}", memberId);
    return ResponseEntity.ok(ApiResult.OK(response));
  }
}
