package com.ssafy.enjoytrip.match.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchDto {
	private String matchId;
	private String courseId;
	private String authorId;
	private String matchTitle;
	private String travelStartDate;
	private String travelEndDate;
	private String maxPeople;
	private String genderType;
	private String deadLine;
	private String hit;
	private int deleted;
}
