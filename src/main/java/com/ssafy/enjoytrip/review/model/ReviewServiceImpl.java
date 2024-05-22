package com.ssafy.enjoytrip.review.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
	public void writeReview(ReviewAddDto reviewDto) throws Exception {
		reviewMapper.writeReview(reviewDto.getReview());
		if (reviewDto.getFileIds().size() > 0) {
			List<ReviewFileDto> files = new ArrayList<>();
			for (int fileId : reviewDto.getFileIds()) {
				ReviewFileDto rfdto = new ReviewFileDto("0", reviewDto.getReview().getReviewId() + "", fileId + "");
				System.out.println(rfdto);
				files.add(rfdto);
			}
			
			reviewMapper.mappingFiles(files);
		}
		
//		List<FileInfoDto> fileInfos = reviewDto.getFileInfos();
//		if (fileInfos != null && !fileInfos.isEmpty()) {
//			reviewMapper.registerFile(reviewDto);
//		}
	}

	@Override
	public ReviewListDto listReview(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keyword", map.get("keyword") == null ? "" : map.get("keyword"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "9" : map.get("spp"));
		int sortKey = Integer.parseInt(map.get("sortKey") == null ? "0" : map.get("sortKey"));
		int scopeKey = Integer.parseInt(map.get("scopeKey") == null ? "0" : map.get("scopeKey"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);
		param.put("sortKey", sortKey);
		param.put("scopeKey", scopeKey);
		
		List<ReviewDto> list = reviewMapper.listReview(param);
		
		int totalArticleCount = reviewMapper.getTotalReviewCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		ReviewListDto reviewListDto = new ReviewListDto();
		reviewListDto.setReviews(list);
		reviewListDto.setCurrentPage(currentPage);
		reviewListDto.setTotalPageCount(totalPageCount);

//		System.out.println(start + " " + sizePerPage);
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
	public void updateReview(ReviewAddDto reviewDto) throws Exception {
		reviewMapper.updateReview(reviewDto.getReview());
		if (reviewDto.getFileIds().size() > 0) {
			List<ReviewFileDto> files = new ArrayList<>();
			for (int fileId : reviewDto.getFileIds()) {
				ReviewFileDto rfdto = new ReviewFileDto("0", reviewDto.getReview().getReviewId() + "", fileId + "");
				System.out.println(rfdto);
				files.add(rfdto);
			}
			
			reviewMapper.mappingFiles(files);
		}
	}

	@Override
	public void deleteReview(int viewId) throws Exception {
		reviewMapper.deleteReview(viewId);
	}

	@Override
	public void updateLikeCount(Map<String,Object> res) throws Exception {
		reviewMapper.updateLikeCount(res);
	}

	@Override
	public ReviewMemberLikesDto selectLikeCount(ReviewMemberLikesDto dto) throws Exception {
		return reviewMapper.selectLikeCount(dto);
	}

	@Override
	public void insertLikeCount(ReviewMemberLikesDto dto) throws Exception {
		reviewMapper.insertLikeCount(dto);
	}

	@Override
	public void deleteLikeCount(ReviewMemberLikesDto dto) throws Exception {
		reviewMapper.deleteLikeCount(dto);
	}

	@Override
	public Integer getRelation(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return reviewMapper.getRelation(map);
	}

	@Override
	public List<ReviewDto> getRecentlyReview(Map<String, String> map) throws Exception {
		return reviewMapper.getRecentlyReview(map);
	}

	@Override
	public List<ReviewDto> getFollowReview(Map<String, String> map) throws Exception {
		return reviewMapper.getFollowReview(map);
	}
	
    @Override
    public List<ReviewDto> getLikedReviews(Map<String, String> map) throws Exception {
    	System.out.println(map.toString());
        return reviewMapper.getLikedReviews(map);
    }
	
	
	//댓글
	@Override
	public void writeComment(CommentDto dto) throws Exception {
		reviewMapper.writeComment(dto);
	}

	@Override
	public void setCommentGroup(String commentId) throws Exception {
		reviewMapper.setCommentGroup(commentId);
	}

	@Override
	public List<CommentDto> commentList(int viewId) throws Exception {
		List<CommentDto> comments = reviewMapper.commentList(viewId);
		Collections.sort(comments);
		return comments;
	}

	@Override
	public void writeReply(CommentDto dto) throws Exception {
		reviewMapper.writeReply(dto);
	}

	@Override
	public String getReplyParentName(String commentId) throws Exception {
		return reviewMapper.getReplyParentName(commentId);
	}

	@Override
	public void updateComment(CommentDto dto) throws Exception {
		reviewMapper.updateComment(dto);
	}

	@Override
	public void deleteComment(int commentId) throws Exception {
		reviewMapper.deleteComment(commentId);
		
	}
	
    @Override
    public int getReviewCount(int memberId) throws Exception {
        return reviewMapper.getReviewCount(memberId);
    }

}
