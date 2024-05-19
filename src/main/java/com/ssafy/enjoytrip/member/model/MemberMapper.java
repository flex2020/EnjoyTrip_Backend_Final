package com.ssafy.enjoytrip.member.model;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
	MemberDto findByEmail(String email) throws SQLException;
	void insertMember(MemberDto memberDto) throws SQLException;
}
