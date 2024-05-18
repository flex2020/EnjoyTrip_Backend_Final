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

import com.ssafy.enjoytrip.review.model.ReviewDto;
import com.ssafy.enjoytrip.review.model.ReviewListDto;
import com.ssafy.enjoytrip.review.model.ReviewService;
import com.ssafy.enjoytrip.review.model.ReviewViewDto;

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
	public ResponseEntity<ReviewViewDto> getArticle(
			@PathVariable("viewid") int viewId)
			throws Exception {
		reviewService.updateHit(viewId);
		return new ResponseEntity<ReviewViewDto>(reviewService.getReviewView(viewId), HttpStatus.OK);
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
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
