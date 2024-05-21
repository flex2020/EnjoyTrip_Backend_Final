package com.ssafy.enjoytrip.follow.model;

import java.util.List;

import com.ssafy.enjoytrip.member.model.MemberDto;

public interface FollowService {
    void follow(FollowRequestDto followRequestDto) throws Exception;
    void unfollow(FollowRequestDto followRequestDto) throws Exception;
    int getFollowerCount(FollowCountRequestDto followCountRequestDto) throws Exception;
    int getFolloweeCount(FollowCountRequestDto followCountRequestDto) throws Exception;
    boolean isEmailExist(String email) throws Exception;
    int getFollowRelation(FollowRelationRequestDto followRelationRequestDto) throws Exception;
    List<MemberDto> getFollowers(int memberId) throws Exception;
    List<MemberDto> getFollowees(int memberId) throws Exception;
}
