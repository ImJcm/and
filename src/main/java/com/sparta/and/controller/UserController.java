package com.sparta.and.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.and.dto.response.UserResponseDto;
import com.sparta.and.dto.response.UserSearchResponseDto;
import com.sparta.and.jwt.JwtUtil;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.GoogleService;
import com.sparta.and.service.KakaoService;
import com.sparta.and.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	private final KakaoService kakaoService;
	private final GoogleService googleService;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<UserResponseDto> getUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(userService.getUser(userDetails));
	}

	@GetMapping("/kakao/callback")
	public String KakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
		String token = kakaoService.kakaoLogin(code); // 반환 값이 JWT 토큰
		token = token.substring(7);
		token = "Bearer%20" + token;
		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
		cookie.setPath("/");
		response.addCookie(cookie);

		return "redirect:/"; //프론트 연결;
	}

	@GetMapping("/google/callback")
	public String googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
		String token = googleService.googleLogin(code); // 반환 값이 JWT 토큰

		token = token.substring(7);
		token = "Bearer%20" + token;
		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
		cookie.setPath("/");
		response.addCookie(cookie);

		return "redirect:/";
	}

	@GetMapping("/search")
	public ResponseEntity<List<UserSearchResponseDto>> searchUser(@RequestParam String keyword, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(userService.searchUsers(keyword));
	}

	//로그아웃은 토큰 만료로 할 것인가, 레디스로 할 것인가... 나중에 구현하겠습니다.
}