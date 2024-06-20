package com.ssafy.enjoytrip.chat.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoticeDto {
	private String type;
	private int result;
	private int idx;
}
