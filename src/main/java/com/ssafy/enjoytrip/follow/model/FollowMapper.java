package com.ssafy.enjoytrip.follow.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FollowMapper {
    void insertFollow(@Param("fromEmail") String fromEmail, @Param("toEmail") String toEmail);
    void deleteFollow(@Param("fromEmail") String fromEmail, @Param("toEmail") String toEmail);
    int countFollowers(@Param("email") String email);
    int countFollowees(@Param("email") String email);
    boolean isFollowing(@Param("fromMemberId") int fromMemberId, @Param("toMemberId") int toMemberId);
    boolean isFollowedBy(@Param("fromMemberId") int fromMemberId, @Param("toMemberId") int toMemberId);
}
