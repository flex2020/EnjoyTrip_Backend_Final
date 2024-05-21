package com.ssafy.enjoytrip.match.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchListItemDto {
	private String matchId;
	private String matchTitle;
	private String hashtagNames;
	private String filePath;
	private String hit;
}
