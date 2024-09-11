package com.point.emotiondiarypoint.service;

import com.point.emotiondiarypoint.domain.Point;
import com.point.emotiondiarypoint.domain.PointHistory;
import com.point.emotiondiarypoint.dto.PointDto;
import com.point.emotiondiarypoint.dto.PointHistoryDto;
import com.point.emotiondiarypoint.mapper.PointHistoryMapper;
import com.point.emotiondiarypoint.mapper.PointMapper;
import com.point.emotiondiarypoint.repository.PointHistoryRepository;
import com.point.emotiondiarypoint.repository.PointRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PointService {

  private final PointRepository repository;
  private final PointMapper mapper;
  private final PointHistoryRepository pointHistoryRepository;
  private final PointHistoryMapper pointHistoryMapper;

  @Transactional
  public PointDto save(PointDto pointDto) {
    Point savedPoint = repository.save(mapper.toEntity(pointDto));
    return mapper.toDto(savedPoint);
  }

  @Transactional
  public PointHistoryDto add(PointHistoryDto pointHistoryDto) {
    Point point = repository.getReferenceById(pointHistoryDto.getPointId());
    PointHistory pointHistory = pointHistoryRepository.save(pointHistoryMapper.toEntity(pointHistoryDto, point));
    return pointHistoryMapper.toDto(pointHistory);
  }

  public PointDto get(Long memberId) {
    return mapper.toDto(
        repository.findByMemberId(memberId).orElseThrow()
    );
  }

  @Transactional
  public PointDto getOrSave(Long memberId) {
    Optional<Point> optional = repository.findByMemberId(memberId);
    return optional.isPresent() ?
        mapper.toDto(optional.get()) :
        mapper.toDto(repository.save(Point.of(memberId)));
  }
}
