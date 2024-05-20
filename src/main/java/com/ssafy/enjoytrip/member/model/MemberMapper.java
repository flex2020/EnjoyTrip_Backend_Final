package com.ssafy.enjoytrip.member.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
	MemberDto findByEmail(String email) throws SQLException;
	void insertMember(MemberDto memberDto) throws SQLException;
	MemberDto findByEmailAndName(@Param("email") String email, @Param("name") String name) throws SQLException;
	void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto) throws SQLException;
	void updateMember(MemberDto memberDto) throws SQLException;
}
