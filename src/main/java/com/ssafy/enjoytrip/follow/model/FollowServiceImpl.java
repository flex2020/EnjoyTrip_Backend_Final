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
        if (isEmailExist(followCountRequestDto.getEmail())) {
            return followMapper.countFollowers(followCountRequestDto.getEmail());
        } else {
            log.error("Invalid email address");
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    @Override
    public int getFolloweeCount(FollowCountRequestDto followCountRequestDto) throws SQLException {
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
}
