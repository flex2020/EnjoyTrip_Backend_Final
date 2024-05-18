package com.ssafy.enjoytrip.review.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewMapper reviewMapper;

	@Autowired
	public ReviewServiceImpl(ReviewMapper reviewMapper) {
		super();
		this.reviewMapper = reviewMapper;
	}

	@Override
	@Transactional
	public void writeReview(ReviewDto reviewDto) throws Exception {
		reviewMapper.writeReview(reviewDto);
//		List<FileInfoDto> fileInfos = reviewDto.getFileInfos();
//		if (fileInfos != null && !fileInfos.isEmpty()) {
//			reviewMapper.registerFile(reviewDto);
//		}
	}

	@Override
	public ReviewListDto listReview(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "9" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		List<ReviewDto> list = reviewMapper.listReview(param);
		
		int totalArticleCount = reviewMapper.getTotalReviewCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		ReviewListDto reviewListDto = new ReviewListDto();
		reviewListDto.setReviews(list);
		reviewListDto.setCurrentPage(currentPage);
		reviewListDto.setTotalPageCount(totalPageCount);
		
//		System.out.println(start+" "+sizePerPage);
//		System.out.println(list.toString());
		
		return reviewListDto;
	}

	@Override
	public void updateHit(int viewId) throws Exception {
		// TODO Auto-generated method stub
		reviewMapper.updateHit(viewId);
	}

	@Override
	public ReviewViewDto getReviewView(int viewId) throws Exception {

		return reviewMapper.getReviewView(viewId);
	}

	@Override
	public ReviewDto getReviewUpdate(int viewId) throws Exception {
		return reviewMapper.getReviewUpdate(viewId);
	}

	@Override
	public void updateReview(ReviewDto reviewDto) throws Exception {
		reviewMapper.updateReview(reviewDto);
	}

	@Override
	public void deleteReview(int viewId) throws Exception {
		reviewMapper.deleteReview(viewId);
	}

}
