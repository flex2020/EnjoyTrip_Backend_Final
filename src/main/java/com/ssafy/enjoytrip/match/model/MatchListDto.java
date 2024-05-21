package com.ssafy.enjoytrip.match.model;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchListDto {
	private List<Map<String, Object>> matches;
	private int currentPage;
	private int totalPageCount;
}
