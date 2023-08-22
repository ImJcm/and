package com.sparta.and.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.and.dto.request.ContestPostRequestDto;
import com.sparta.and.dto.response.ContestPostResponseDto;
import com.sparta.and.entity.ContestPost;
import com.sparta.and.repository.ContestPostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContestPostService {

	private final ContestPostRepository contestPostRepository;

	public List<ContestPostResponseDto> getContests() {
		return contestPostRepository.findAllByOrderByCreatedDateDesc().stream().map(ContestPostResponseDto::new).toList();
	}

	public ContestPostResponseDto createContest (ContestPostRequestDto requestDto){
		ContestPost createContest = new ContestPost(requestDto);
		contestPostRepository.save(createContest);

		return new ContestPostResponseDto(createContest);
	}


}
