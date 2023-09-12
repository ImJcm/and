package com.sparta.and.controller;

import com.sparta.and.dto.response.FileResponse;
import com.sparta.and.service.FileUploadService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//@Api(value = "이미지 저장 관련 API", tags = {"CKController"}, description = "이미지 저장 컨트롤러")
@RestController
@Slf4j
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CKController {

	private final FileUploadService fileUploadService;

	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUploadFromCKEditor(HttpServletResponse response,
	                                                           @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {

		/**
		 * @Method Name : fileUploadFromCKEditor
		 * @작성자 :
		 * @Method 설명 : 이미지를 받아 s3에 저장 후 url을 json 형태로 반환.
		 */
		return new ResponseEntity<>(FileResponse.builder().
				uploaded(true).
				url(fileUploadService.upload(image)).
				build(), HttpStatus.OK);
	}
}
