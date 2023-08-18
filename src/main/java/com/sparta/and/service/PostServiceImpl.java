package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.PostRequestDto;
import com.sparta.and.dto.response.PostResponseDto;
import com.sparta.and.entity.Post;
import com.sparta.and.repository.PostRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "PostService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	@Override
	public PostResponseDto createPost(PostRequestDto requestDto, UserDetailsImpl userDetails) {
		log.info("Service - createPost : 시작");

		Post post = postRepository.save(new Post(requestDto, userDetails.getUser()));

		log.info("Service - createPost : 끝");
		return new PostResponseDto(post);
	}

	@Override
	@Transactional
	public PostResponseDto getPost(Long id) {
		log.info("Service - getPost : 시작");

		Post Post = findPost(id);

		// TODO : 사용자 확인한 뒤 작성자라면 조회수 증가x 추가
		long views = Post.getPostviews() + 1;
		log.info("views : " + views);
		Post.setPostviews(views);
		log.info("views : " + views);

		log.info("Service - getPost : 끝");
		return new PostResponseDto(Post);
	}

	@Override
	@Transactional
	public PostResponseDto modifyPost(Long id, PostRequestDto requestDto) {
		log.info("Service - modifyPost : 시작");

		Post Post = findPost(id);

		// TODO : 작성자 확인 추가
		Post.setTitle(requestDto.getTitle());
		Post.setContents(requestDto.getContents());

		log.info("Service - modifyPost : 끝");
		return new PostResponseDto(Post);
	}

	@Override
	public ApiResponseDto deletePost(Long id) {
		log.info("Service - deletePost : 시작");

		Post Post = findPost(id);

		// TODO : 작성자 확인 추가
		postRepository.delete(Post);

		log.info("Service - deletePost : 끝");
		return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
	}

	@Override
	public Post findPost(Long id) {
		return postRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 글입니다.")
		);
	}
}
