package com.ssafy.enjoytrip.file.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

public interface FileService {
	FileDto uploadFile(MultipartFile file, ServletContext servletContext) throws Exception;
}
