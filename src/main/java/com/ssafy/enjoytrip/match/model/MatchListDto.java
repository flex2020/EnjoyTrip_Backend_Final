package com.ssafy.enjoytrip.match.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MatchListDto {
	private List<MatchDto> matches;
	private int currentPage;
	private int totalPageCount;
}
