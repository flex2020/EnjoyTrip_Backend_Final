package com.ssafy.enjoytrip.file.model;

import lombok.Data;

@Data
public class FileDto {
	private String fileId;
	private String fileName;
	private long fileSize;
	private String filePath;
}
