package com.point.emotiondiarypoint.error.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

  String name();

  HttpStatus getHttpStatus();

  String getMessage();

  String getErrorCode();
}
