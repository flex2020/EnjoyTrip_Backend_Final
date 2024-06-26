package com.ssafy.enjoytrip.review.controller;

import java.util.HashMap;
import java.util.List;
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
import com.ssafy.enjoytrip.review.model.ReviewAddDto;
import com.ssafy.enjoytrip.review.model.ReviewDto;
import com.ssafy.enjoytrip.review.model.ReviewListDto;
import com.ssafy.enjoytrip.review.model.ReviewMemberLikesDto;
import com.ssafy.enjoytrip.review.model.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/review")
@Slf4j
public class ReviewController {
	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}
	
	@PostMapping
	public ResponseEntity<?> writeReview(
			@RequestBody ReviewAddDto reviewDto) {
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
//			System.out.println(reviewListDto.getReviews().size());
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
		System.out.println(response);
//		System.out.println(response.get("comments").toString());
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
			@RequestBody ReviewAddDto reviewDto)
			throws Exception {
		System.out.println(reviewDto);
		reviewService.updateReview(reviewDto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{viewid}")
	public ResponseEntity<String> deleteReview(@PathVariable("viewid") int viewId) throws Exception {
		reviewService.deleteReview(viewId);
		return ResponseEntity.ok().build();
	}
	
//	@GetMapping("/likecount/{viewid}")
//	public ResponseEntity<Boolean> getLikeCount(
//			@PathVariable("viewid") int viewId)
//			throws Exception {
//		ReviewMemberLikesDto reviewMemberLikeDto = reviewService.selectLikeCount(viewId);
//		if (reviewMemberLikeDto == null) {
//			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
//		}
//	}
	
	@PostMapping("/get/likecount")
	public ResponseEntity<Boolean> getLikeCount(
			@RequestBody ReviewMemberLikesDto dto)
			throws Exception {
		ReviewMemberLikesDto reviewMemberLikeDto = reviewService.selectLikeCount(dto);
//		System.out.println(reviewMemberLikeDto.toString());
		if (reviewMemberLikeDto == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);
		} else {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
	}
	
	@PostMapping("/likecount")
	public ResponseEntity<String> updateLikeCount(@RequestBody ReviewMemberLikesDto dto) throws Exception {
		ReviewMemberLikesDto reviewMemberLikeDto = reviewService.selectLikeCount(dto);
//		System.out.println(reviewMemberLikeDto);
//		System.out.println(Integer.parseInt(dto.getReviewId()));
		Map<String,Object> res = new HashMap();
		if (reviewMemberLikeDto == null) {
			reviewService.insertLikeCount(dto);
			res.put("viewId", dto.getReviewId());
			res.put("memberId", dto.getMemberId());
			res.put("order", "up");
		} else {
			reviewService.deleteLikeCount(dto);
			res.put("viewId", dto.getReviewId());
			res.put("memberId", dto.getMemberId());
			res.put("order", "down");
		}
		reviewService.updateLikeCount(res);
		return new ResponseEntity<String>((String) res.get("order"), HttpStatus.OK);
	}
	
	@GetMapping("/recently")
	public ResponseEntity<ReviewDto> getRecentlyReview(
			@RequestParam Map<String, String> map)
			throws Exception {
		// 관계 파악 (관계가 있는지, 관계가 없는지)
		if(map.get("loginUserId").equals(map.get("targetUserId"))) {
			map.put("isRelation", "2");
		} else {
			Integer isRelation = reviewService.getRelation(map);
			map.put("isRelation", isRelation == 0 ? "0" : "1");
		}
		List<ReviewDto> list = reviewService.getRecentlyReview(map);
		return new ResponseEntity<ReviewDto>(list.size() != 0 ? list.get(0) : new ReviewDto() , HttpStatus.OK);
	}
	
	@GetMapping("/getfollowreviews")
	public ResponseEntity<List<ReviewDto>> getFollowReview(
			@RequestParam Map<String, String> map) throws Exception {
		log.info(map.toString());
		// 관계 파악 (관계가 있는지, 관계가 없는지)
		if(map.get("loginUserId").equals(map.get("targetUserId"))) {
			map.put("isRelation", "2");
		} else {
			Integer isRelation = reviewService.getRelation(map);
			map.put("isRelation", isRelation == 0 ? "0" : "1");
		}
		return new ResponseEntity<List<ReviewDto>>(reviewService.getFollowReview(map), HttpStatus.OK);
	}
	
    @GetMapping("/getlikereviews")
    public ResponseEntity<List<ReviewDto>> getLikedReview(@RequestParam Map<String, String> map) throws Exception {
    	Map<String, String> customMap = new HashMap<>();
    	log.info(map.toString());
    	customMap.put("memberId", map.get("targetUserId"));
    	log.info(customMap.toString());
    	return new ResponseEntity<List<ReviewDto>>(reviewService.getLikedReviews(customMap), HttpStatus.OK);
    }
	
	// 댓글
	@PostMapping("/comments")
	public ResponseEntity<?> writeComment(@RequestBody CommentDto dto) throws Exception {
		dto.setDepth("0");
		reviewService.writeComment(dto);
		reviewService.setCommentGroup(dto.getCommentId());
		return ResponseEntity.ok("댓글 작성 완료");
	}
	
	@PostMapping("/comments-reply")
	public ResponseEntity<?> commentReply(@RequestBody CommentDto dto) throws Exception {
		dto.setReplyParentName(reviewService.getReplyParentName(dto.getCommentId()));
		reviewService.writeReply(dto);
		return ResponseEntity.ok("답글 작성 완료");
	}
	
	@PutMapping("/comments")
	public ResponseEntity<?> commentUpdate(@RequestBody CommentDto dto, HttpSession session) throws Exception {
//		System.out.println(dto);
		reviewService.updateComment(dto);
		return ResponseEntity.ok("댓글 수정 완료");
	}
	
	@DeleteMapping("/comments/{commentId}")
	public  ResponseEntity<?> commentDelete(@PathVariable("commentId") int commentId) throws Exception {
		reviewService.deleteComment(commentId);
		return ResponseEntity.ok("댓글 삭제 완료");
	}
	
	@PostMapping("/count")
    public ResponseEntity<Integer> getReviewCount(@RequestBody Map<String, String> request) throws Exception {
        int memberId = Integer.parseInt(request.get("memberId"));
        int count = reviewService.getReviewCount(memberId);
        return ResponseEntity.ok(count);
    }
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
