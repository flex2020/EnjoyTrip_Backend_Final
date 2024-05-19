package com.ssafy.enjoytrip.email.model;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailMapper {
	void insertEmailVerification(@Param("email") String email, @Param("key") String key, @Param("time") String time) throws SQLException;
	void deleteExpiredCodes();
    String getEmailVerificationCode(@Param("email") String email);
}
