package com.ssafy.enjoytrip.trip.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttractionDto {
	private String contentId;
    private String contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private String sidoCode;
    private String gugunCode;
    private String latitude;
    private String longitude;
}
