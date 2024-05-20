package com.ssafy.enjoytrip.file.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
	void uploadFile(FileDto fileDto);
}
