package com.sparta.and.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.and.dto.request.ContestBoardRequestDto;
import com.sparta.and.dto.response.ContestBoardResponseDto;
import com.sparta.and.entity.ContestPost;
import com.sparta.and.repository.ContestPostRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContestPostService {

	private final ContestPostRepository contestBoardRepository;

	public List<ContestBoardResponseDto> getContests() {
		return contestBoardRepository.findAllByOrderByCreatedDateDesc().stream().map(ContestBoardResponseDto::new).toList();
	}

	public ContestBoardResponseDto createContest (ContestBoardRequestDto requestDto){
		ContestPost createContest = new ContestPost(requestDto);
		contestBoardRepository.save(createContest);

		return new ContestBoardResponseDto(createContest);
	}


}
