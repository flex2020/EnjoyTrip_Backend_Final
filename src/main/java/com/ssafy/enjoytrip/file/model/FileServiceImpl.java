package com.ssafy.enjoytrip.file.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
	private final FileMapper fileMapper;

	@Override
	public FileDto uploadFile(MultipartFile file, ServletContext servletContext) throws Exception {
		String realPath = servletContext.getRealPath("/WEB-INF/img");
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = realPath + File.separator + today;
		File folder = new File(saveFolder);
		if (!folder.exists()) folder.mkdirs();
		String ext = file.getOriginalFilename().split("\\.")[1];
		String uuid = UUID.randomUUID().toString();
		FileDto dto = new FileDto();
		dto.setFileName(file.getOriginalFilename());
		dto.setFilePath("/api/files/" + today + "/" + uuid + "." + ext);
		dto.setFileSize(file.getSize());
		System.out.println(folder.getPath());
		file.transferTo(new File(folder, uuid + "." + ext));
	
		fileMapper.uploadFile(dto);
		return dto;
	}
}
