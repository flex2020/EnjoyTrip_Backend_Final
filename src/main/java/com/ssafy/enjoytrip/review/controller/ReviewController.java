package com.ssafy.enjoytrip.review.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.review.model.CommentDto;
import com.ssafy.enjoytrip.review.model.ReviewDto;
import com.ssafy.enjoytrip.review.model.ReviewListDto;
import com.ssafy.enjoytrip.review.model.ReviewMemberLikesDto;
import com.ssafy.enjoytrip.review.model.ReviewService;
import com.ssafy.enjoytrip.review.model.ReviewViewDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {
	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}
	
	@PostMapping
	public ResponseEntity<?> writeReview(
			@RequestBody ReviewDto reviewDto) {
		System.out.println(reviewDto.toString());
		try {
			reviewService.writeReview(reviewDto);
//			return ResponseEntity.ok().build();
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> listReview(
			@RequestParam Map<String, String> map
			) {
		// logic 처리
		try {
			ReviewListDto reviewListDto = reviewService.listReview(map);
			Map<String,Object> res = new HashMap();
			res.put("msg", "조회완료");
			res.put("resdata", reviewListDto);
			ResponseEntity<Map<String,Object>>resp = 
					new ResponseEntity<Map<String,Object>>(res,HttpStatus.OK);
			return resp;
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{viewid}")
	public ResponseEntity<Map<String, Object>> getReview(
			@PathVariable("viewid") int viewId)
			throws Exception {
		Map<String, Object> response = new HashMap<>();
		response.put("reviewView", reviewService.getReviewView(viewId));
		response.put("comments", reviewService.commentList(viewId));
		reviewService.updateHit(viewId);
		System.out.println(response.get("comments").toString());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@GetMapping("/update/{viewid}")
	public ResponseEntity<ReviewDto> getUpdateReview(
			@PathVariable("viewid") int viewId)
			throws Exception {
		return new ResponseEntity<ReviewDto>(reviewService.getReviewUpdate(viewId), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> updateReview(
			@RequestBody ReviewDto reviewDto)
			throws Exception {
		reviewService.updateReview(reviewDto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{viewid}")
	public ResponseEntity<String> deleteReview(@PathVariable("viewid") int viewId) throws Exception {
		reviewService.deleteReview(viewId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/likecount/{viewid}")
	public ResponseEntity<Boolean> getLikeCount(
			@PathVariable("viewid") int viewId)
			throws Exception {
		ReviewMemberLikesDto reviewMemberLikeDto = reviewService.selectLikeCount(viewId);
		if (reviewMemberLikeDto == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	}
	
	@PostMapping("/likecount/{viewid}")
	public ResponseEntity<String> updateLikeCount(@PathVariable("viewid") int viewId) throws Exception {
		ReviewMemberLikesDto reviewMemberLikeDto = reviewService.selectLikeCount(viewId);
//		System.out.println(reviewMemberLikeDto);
		Map<String,Object> res = new HashMap();
		if (reviewMemberLikeDto == null) {
			reviewService.insertLikeCount(viewId);
			res.put("viewId", viewId);
			res.put("order", "up");
		} else {
			reviewService.deleteLikeCount(viewId);
			res.put("viewId", viewId);
			res.put("order", "down");
		}
		reviewService.updateLikeCount(res);
		return new ResponseEntity<String>((String) res.get("order"), HttpStatus.OK);
	}
	
	@PostMapping("/comments")
	public ResponseEntity<?> writeComment(@RequestBody CommentDto dto) throws Exception {
		dto.setMemberId("1");
		dto.setDepth("0");
//		System.out.println(dto);
		reviewService.writeComment(dto);
		reviewService.setCommentGroup(dto.getCommentId());
		return ResponseEntity.ok("댓글 작성 완료");
	}
	
	@PostMapping("/comments-reply")
	public ResponseEntity<?> commentReply(@RequestBody CommentDto dto) throws Exception {
		
		dto.setMemberId("2");
		System.out.println(dto.toString());
		reviewService.writeReply(dto);
		return ResponseEntity.ok("답글 작성 완료");
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
