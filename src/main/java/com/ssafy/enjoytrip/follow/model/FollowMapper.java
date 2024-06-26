package com.ssafy.enjoytrip.follow.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.enjoytrip.member.model.MemberDto;

@Mapper
public interface FollowMapper {
    void insertFollow(@Param("fromMemberId") String fromMemberId, @Param("toMemberId") String toMemberId);
    void deleteFollow(@Param("fromMemberId") String fromMemberId, @Param("toMemberId") String toMemberId);
    int countFollowers(@Param("memberId") String memberId);
    int countFollowees(@Param("memberId") String memberId);
    boolean isFollowing(@Param("fromMemberId") int fromMemberId, @Param("toMemberId") int toMemberId);
    boolean isFollowedBy(@Param("fromMemberId") int fromMemberId, @Param("toMemberId") int toMemberId);
    List<FollowProfileDto> getFollowers(@Param("memberId") int memberId);
    List<FollowProfileDto> getFollowees(@Param("memberId") int memberId);
}
