package com.ssafy.enjoytrip.follow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.email.controller.EmailController;
import com.ssafy.enjoytrip.email.model.EmailService;
import com.ssafy.enjoytrip.follow.model.FollowCountRequestDto;
import com.ssafy.enjoytrip.follow.model.FollowCountResponseDto;
import com.ssafy.enjoytrip.follow.model.FollowRequestDto;
import com.ssafy.enjoytrip.follow.model.FollowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/follow")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody FollowRequestDto followRequestDto) throws Exception {
        followService.follow(followRequestDto);
        return ResponseEntity.ok("follow success");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollow(@RequestBody FollowRequestDto followRequestDto) throws Exception {
        followService.unfollow(followRequestDto);
        return ResponseEntity.ok("unfollow success");
    }

    @PostMapping("/followers/count")
    public ResponseEntity<FollowCountResponseDto> getFollowerCount(@RequestBody FollowCountRequestDto followCountRequestDto) throws Exception {
        int count = followService.getFollowerCount(followCountRequestDto);
        FollowCountResponseDto responseDto = FollowCountResponseDto.builder()
        		.count(count)
        		.build();
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/followees/count")
    public ResponseEntity<FollowCountResponseDto> getFolloweeCount(@RequestBody FollowCountRequestDto followCountRequestDto) throws Exception {
        int count = followService.getFolloweeCount(followCountRequestDto);
        FollowCountResponseDto responseDto = FollowCountResponseDto.builder()
        		.count(count)
        		.build();
        return ResponseEntity.ok(responseDto);
    }
}