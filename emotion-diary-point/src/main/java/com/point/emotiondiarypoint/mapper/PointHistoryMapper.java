package com.point.emotiondiarypoint.mapper;

import com.point.emotiondiarypoint.api.request.PointHistoryRequest;
import com.point.emotiondiarypoint.api.response.PointHistoryResponse;
import com.point.emotiondiarypoint.domain.Point;
import com.point.emotiondiarypoint.domain.PointHistory;
import com.point.emotiondiarypoint.dto.PointHistoryDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointHistoryMapper {

  @Mapping(target = "point", expression = "java(point)")
  PointHistory toEntity(PointHistoryDto dto, @Context Point point);

  PointHistoryDto toDto(PointHistory entity);

  PointHistoryDto toDto(PointHistoryRequest request);

  PointHistoryResponse toResponse(PointHistoryDto dto);
}
