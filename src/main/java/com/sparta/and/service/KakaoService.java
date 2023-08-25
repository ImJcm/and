package com.sparta.and.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.and.dto.KakaoUserInfoDto;
import com.sparta.and.entity.User;
import com.sparta.and.entity.UserBlackList;
import com.sparta.and.jwt.JwtUtil;
import com.sparta.and.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "Kakao Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RestTemplate restTemplate;
	private final JwtUtil jwtUtil;
	private final UserBlackList userBlackList;
	@Value("${auth.kakao.client_id}")
	private String restApiKey;
	@Value("${auth.kakao.redirectURL}")
	private String redirectURL;

	public String kakaoLogin(String code) throws JsonProcessingException {
		// "인가 코드"로 "액세스 토큰" 요청
		String accessToken = getToken(code);

		// 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
		KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

		// 필요시 회원가입
		User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

		// Jwt 토큰 반환
		String createToken = jwtUtil.createToken(kakaoUser.getUserName());
		return createToken;
	}

	private String getToken(String code) throws JsonProcessingException {
		log.info("인가코드: " + code);
		// 요청 URL 만들기
		URI uri = UriComponentsBuilder
				.fromUriString("https://kauth.kakao.com")
				.path("/oauth/token")
				.encode()
				.build()
				.toUri();

		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HTTP Body 생성
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", restApiKey);
		body.add("redirect_uri", redirectURL);
		body.add("code", code);

		RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
				.post(uri)
				.headers(headers)
				.body(body);

		// HTTP 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
				requestEntity,
				String.class
		);

		// HTTP 응답 (JSON) -> 액세스 토큰 파싱
		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		return jsonNode.get("access_token").asText();
	}

	private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
		log.info("access token: " + accessToken);
		// 요청 URL 만들기
		URI uri = UriComponentsBuilder
				.fromUriString("https://kapi.kakao.com")
				.path("/v2/user/me")
				.encode()
				.build()
				.toUri();

		// HTTP Header 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
				.post(uri)
				.headers(headers)
				.body(new LinkedMultiValueMap<>());

		// HTTP 요청 보내기
		ResponseEntity<String> response = restTemplate.exchange(
				requestEntity,
				String.class
		);

		JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
		Long id = jsonNode.get("id").asLong();
		String nickname = jsonNode.get("properties")
				.get("nickname").asText();
		String username = jsonNode.get("kakao_account")
				.get("email").asText();

		log.info("카카오 사용자 정보: " + id + ", " + nickname + ", " + username);
		return new KakaoUserInfoDto(id, nickname, username);
	}

	private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
		// DB 에 중복된 Kakao Id 가 있는지 확인
		Long kakaoId = kakaoUserInfo.getId();
		User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

		if (kakaoUser == null) {
			// 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
			String kakaoUsername = kakaoUserInfo.getUsername();
			User sameUsername = userRepository.findByUserName(kakaoUsername).orElse(null);

			// 이거 테스트 안되면 밑에걸로 ㄱㄱ
			if(kakaoUsername.equals(userBlackList.getUser().getUserName())) {
				throw new IllegalArgumentException("응 안돼 돌아가.");
			}

//			if(kakaoUserInfo.getUsername().equals(userBlackList.getUser().getUserName())) {
//				throw new IllegalArgumentException("응 안돼 돌아가.");
//			}

			if (sameUsername != null) {
				kakaoUser = sameUsername;
				// 기존 회원정보에 카카오 Id 추가
				kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
			} else {
				// 신규 회원가입
				// password: random UUID
				String password = UUID.randomUUID().toString();
				String encodedPassword = passwordEncoder.encode(password);

				// email: kakao email
				String username = kakaoUserInfo.getUsername();

				kakaoUser = new User(username, encodedPassword, kakaoUserInfo.getNickname(), kakaoId);
			}

			userRepository.save(kakaoUser);
		}
		return kakaoUser;
	}
}