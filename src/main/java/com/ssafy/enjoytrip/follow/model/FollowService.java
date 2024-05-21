package com.ssafy.enjoytrip.follow.model;

public interface FollowService {
    void follow(FollowRequestDto followRequestDto) throws Exception;
    void unfollow(FollowRequestDto followRequestDto) throws Exception;
    int getFollowerCount(FollowCountRequestDto followCountRequestDto) throws Exception;
    int getFolloweeCount(FollowCountRequestDto followCountRequestDto) throws Exception;
    boolean isEmailExist(String email) throws Exception;
    int getFollowRelation(FollowRelationRequestDto followRelationRequestDto) throws Exception;
}
