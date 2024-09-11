package com.point.emotiondiarypoint.mapper;

import com.point.emotiondiarypoint.api.request.PointRequest;
import com.point.emotiondiarypoint.api.response.PointResponse;
import com.point.emotiondiarypoint.domain.Point;
import com.point.emotiondiarypoint.dto.PointDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PointHistoryMapper.class})
public interface PointMapper {

  @Mapping(target = "pointHistories", source = "historyDtoList")
  Point toEntity(PointDto dto);

  @Mapping(target = "historyDtoList", source = "pointHistories")
  PointDto toDto(Point entity);

  PointDto toDto(PointRequest request);

  @Mapping(target = "historyResponses", source = "historyDtoList")
  PointResponse toResponse(PointDto dto);
}
