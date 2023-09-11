//package com.sparta.and.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.*;
//import com.sparta.and.dto.NaverUserInfoDto;
//import com.sparta.and.entity.*;
//import com.sparta.and.jwt.JwtUtil;
//import com.sparta.and.repository.UserBlackListRepository;
//import com.sparta.and.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.net.URI;
//import java.util.UUID;
//
//@Slf4j(topic = "Naver Login")
//@Service
//@RequiredArgsConstructor
//public class NaverService {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final RestTemplate restTemplate; // 수동 등록한 Bean
//    private final JwtUtil jwtUtil;
//    private final UserBlackListRepository userBlackListRepository;
//
//    @Value("${auth.naver.client_id}")
//    private String restApiKey;
//    @Value("${auth.naver.client_secret}")
//    private String secretKey;
//    @Value("${auth.naver.redirectURL}")
//    private String redirectURL;
//
//    public String naverLogin(String code) throws JsonProcessingException {
//
//        String accessToken = getToken(code);
//
//        log.info("naverLogin accessToken: " + accessToken);
//
//        NaverUserInfoDto naverUserInfoDto = naverUserInfoDto(accessToken);
//
//        User naverUser = registerNaverUserIfNeeded(naverUserInfoDto);
//
//        String createToken = jwtUtil.createToken(naverUser.getUserName());
//
//        return createToken;
//    }
//
//    public String getToken(String code) throws JsonProcessingException {
//        log.info("getToken code: " + code);
//        // 요청 URL 만들기
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://nid.naver.com")
//                .path("/token")
//                .encode()
//                .build()
//                .toUri();
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", restApiKey);
//        body.add("client_secret", secretKey); // 두개 다 해야함
//        body.add("redirect_uri", redirectURL); // 애플리케이션 등록시 설정한 redirect_uri
//        body.add("code",code); // 인가 코드
//
//
//        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
//                .post(uri) // body 가 있으므로 post 메서드
//                .headers(headers)
//                .body(body);
//
//        // HTTP 요청 보내기
//        ResponseEntity<String> response = restTemplate.exchange(
//                requestEntity,
//                String.class // 반환값 타입은 String
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 값을 반환합니다.
//        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
//        return jsonNode.get("access_token").asText();
//    }
//
//    private NaverUserInfoDto naverUserInfoDto(String accessToken) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<NaverUserInfoDto> response = restTemplate.exchange(
//                "https://openapi.naver.com/v1/nid/me",
//                HttpMethod.GET,
//                entity,
//                NaverUserInfoDto.class
//        );
//
//        NaverUserInfoDto userInfo = null;
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            userInfo = response.getBody();
//
//            System.out.println("User ID: " + userInfo.getId());
//            System.out.println("Email: " + userInfo.getEmail());
//
//        } else {
//            System.out.println("Failed to fetch user info");
//        }
//
//        return userInfo;
//
//    }
//
//
//    // 3)  회원가입
//    private User registerNaverUserIfNeeded(NaverUserInfoDto naverUserInfoDto) {
//
//        String naverId = naverUserInfoDto.getId();
//        User naverUser = userRepository.findByNaverId(naverId).orElse(null);
//
//        if (naverUser == null) {
//
//            String NaverEmail = naverUserInfoDto.getEmail();
//            User sameEmailUser = userRepository.findByUserName(NaverEmail).orElse(null);
//            if (sameEmailUser != null) {
//                naverUser = sameEmailUser;
//                naverUser = naverUser.naverIdUpdate(naverId);
//
//                userBlackListRepository.findByUsername(NaverEmail).ifPresent((blackUser) -> {
//                    throw new IllegalArgumentException("블랙리스트 사용자입니다.");
//                });
//            } else {
//                // 신규 회원가입
//                // password: random UUID
//                String password = UUID.randomUUID().toString();
//                String encodedPassword = passwordEncoder.encode(password);
//
//                // email: kakao email
//                String email = naverUserInfoDto.getEmail();
//
//                naverUser = new User(email, encodedPassword, naverUserInfoDto.getNickname(), naverId);
//            }
//
//            userRepository.save(naverUser);
//        }
//
//        return naverUser;
//    }
//}
