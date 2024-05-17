package com.ssafy.enjoytrip.match.model;

import com.ssafy.enjoytrip.course.model.CourseDto;
import com.ssafy.enjoytrip.member.model.MemberDto;

public class MatchDto {
	private String matchId;
	private CourseDto course;
	private MemberDto author;
	private String matchTitle;
	private String travelStartDate;
	private String travelEndDate;
	private String maxPeople;
	private String genderType;
	private String deadLine;
}
