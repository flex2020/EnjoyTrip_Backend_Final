package com.ssafy.enjoytrip.follow.model;

import java.sql.SQLException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.email.model.EmailMapper;
import com.ssafy.enjoytrip.email.model.EmailServiceImpl;
import com.ssafy.enjoytrip.member.model.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;
    private final MemberMapper memberMapper;

    @Override
    public void follow(FollowRequestDto followRequestDto) throws SQLException {
        if (isEmailExist(followRequestDto.getFromEmail()) && isEmailExist(followRequestDto.getToEmail())) {
            followMapper.insertFollow(followRequestDto.getFromEmail(), followRequestDto.getToEmail());
        } else {
            log.error("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    @Override
    public void unfollow(FollowRequestDto followRequestDto) throws SQLException {
        if (isEmailExist(followRequestDto.getFromEmail()) && isEmailExist(followRequestDto.getToEmail())) {
            followMapper.deleteFollow(followRequestDto.getFromEmail(), followRequestDto.getToEmail());
        } else {
            log.error("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    @Override
    public int getFollowerCount(FollowCountRequestDto followCountRequestDto) throws SQLException {
    	log.info(followCountRequestDto.toString());
        if (isEmailExist(followCountRequestDto.getEmail())) {
        	log.info(followCountRequestDto.toString());
            return followMapper.countFollowers(followCountRequestDto.getEmail());
        } else {
            log.error("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    @Override
    public int getFolloweeCount(FollowCountRequestDto followCountRequestDto) throws SQLException {
    	log.info(followCountRequestDto.toString());
        if (isEmailExist(followCountRequestDto.getEmail())) {
            return followMapper.countFollowees(followCountRequestDto.getEmail());
        } else {
            log.error("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    @Override
    public boolean isEmailExist(String email) throws SQLException {
    	if(memberMapper.findByEmail(email)!=null) return true;
        return false;
    }

	@Override
	public int getFollowRelation(FollowRelationRequestDto followRelationRequestDto) throws Exception {
        if (followRelationRequestDto.getMemberId() == followRelationRequestDto.getTargetId()) {
            return 0; // 본인
        }
        
        int memberId = Integer.parseInt(followRelationRequestDto.getMemberId());
        int targetId = Integer.parseInt(followRelationRequestDto.getTargetId());
        
        boolean isFollowing = followMapper.isFollowing(memberId, targetId);
        boolean isFollowedBy = followMapper.isFollowing(targetId, memberId);
        
        log.info(isFollowing + " " + isFollowedBy + " " + memberId + " " + targetId);
        
        if (isFollowing && isFollowedBy) {
            return 3; // 맞팔
        } else if (isFollowing) {
            return 1; // 나만 팔로우
        } else if (isFollowedBy) {
            return 2; // 걔가 날 팔로우
        } else {
            return 0; // 아무 관계 없음
        }
	}
}
