package com.ssafy.enjoytrip.member.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
	MemberDto findByEmail(String email) throws SQLException;
	void insertMember(MemberDto memberDto) throws SQLException;
	MemberDto findByEmailAndName(@Param("email") String email, @Param("name") String name) throws SQLException;
	MemberDto findById(@Param("memberId") String memberId) throws SQLException;
	void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto) throws SQLException;
	void updateDeleteMember(@Param("memberId") int memberId) throws SQLException;
	void updateMember(MemberDto memberDto) throws SQLException;
    Integer getMemberProfileImage(int memberId) throws SQLException;
    void deleteMemberProfileImage(int memberId) throws SQLException;
    void insertMemberProfileImage(@Param("memberId") int memberId, @Param("fileId") int fileId) throws SQLException;
    String findProfileImagePathByMemberId(@Param("memberId") int memberId) throws SQLException;
    void updateMemberScore(@Param("memberId") String memberId, @Param("score") String score) throws SQLException;
}
