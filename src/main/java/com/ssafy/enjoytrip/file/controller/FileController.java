package com.ssafy.enjoytrip.file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.enjoytrip.file.model.FileDto;
import com.ssafy.enjoytrip.file.model.FileService;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class FileController {
	private final FileService fileService;
	private final ServletContext servletContext;
	
	@PostMapping("")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		FileDto result = fileService.uploadFile(file, servletContext);
		return ResponseEntity.ok(result);
	}
}
