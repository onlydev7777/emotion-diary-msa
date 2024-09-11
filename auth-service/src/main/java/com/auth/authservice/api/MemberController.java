package com.auth.authservice.api;

import com.auth.authservice.domain.dto.MemberDto;
import com.auth.authservice.error.ApiResult;
import com.auth.authservice.mapper.MemberMapper;
import com.auth.authservice.service.MemberService;
import com.auth.authservice.service.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

  private final MemberService service;
  private final MemberMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<ApiResult<MemberResponse>> findMember(@PathVariable Long id) {
//    Long memberId = TokenUtil.getMemberId();
    log.debug("start /member/{id}");
//    try {
//      Thread.sleep(3000L);
//      throw new RuntimeException("장애발생!");
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    MemberDto memberDto = service.findById(id);
    log.debug("end /member/{id}");
    return ResponseEntity.ok(ApiResult.OK(mapper.toResponse(memberDto)));
  }
}
