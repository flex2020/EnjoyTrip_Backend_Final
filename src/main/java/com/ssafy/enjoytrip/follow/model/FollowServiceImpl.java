package com.ssafy.enjoytrip.follow.model;

import java.sql.SQLException;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.email.model.EmailMapper;
import com.ssafy.enjoytrip.email.model.EmailServiceImpl;
import com.ssafy.enjoytrip.member.model.MemberDto;
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
        if (isEmailExist(followRequestDto.getFromMemberId()) && isEmailExist(followRequestDto.getToMemberId())) {
            followMapper.insertFollow(followRequestDto.getFromMemberId(), followRequestDto.getToMemberId());
        } else {
            log.error("Invalid memberId");
            throw new IllegalArgumentException("Invalid memberId");
        }
    }

    @Override
    public void unfollow(FollowRequestDto followRequestDto) throws SQLException {
        if (isEmailExist(followRequestDto.getFromMemberId()) && isEmailExist(followRequestDto.getToMemberId())) {
            followMapper.deleteFollow(followRequestDto.getFromMemberId(), followRequestDto.getToMemberId());
        } else {
            log.error("Invalid memberId");
            throw new IllegalArgumentException("Invalid memberId");
        }
    }

    @Override
    public int getFollowerCount(FollowCountRequestDto followCountRequestDto) throws SQLException {
    	log.info(followCountRequestDto.toString());
        if (isEmailExist(followCountRequestDto.getMemberId())) {
        	log.info(followCountRequestDto.toString());
            return followMapper.countFollowers(followCountRequestDto.getMemberId());
        } else {
            log.error("Invalid memberId");
            throw new IllegalArgumentException("Invalid memberId");
        }
    }

    @Override
    public int getFolloweeCount(FollowCountRequestDto followCountRequestDto) throws SQLException {
    	log.info(followCountRequestDto.toString());
        if (isEmailExist(followCountRequestDto.getMemberId())) {
            return followMapper.countFollowees(followCountRequestDto.getMemberId());
        } else {
            log.error("Invalid memberId");
            throw new IllegalArgumentException("Invalid memberId");
        }
    }

    @Override
    public boolean isEmailExist(String memberId) throws SQLException {
    	if(memberMapper.findById(memberId)!=null) return true;
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

    @Override
    public List<MemberDto> getFollowers(int memberId) throws Exception {
        return followMapper.getFollowers(memberId);
    }

    @Override
    public List<MemberDto> getFollowees(int memberId) throws Exception {
        return followMapper.getFollowees(memberId);
    }
}
