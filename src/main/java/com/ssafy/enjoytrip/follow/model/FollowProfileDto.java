package com.ssafy.enjoytrip.follow.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class FollowProfileDto {
    private int memberId;
    private String memberName;
    private String nickname;
    private String profileImage;
    private int relation;
}
