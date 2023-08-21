package com.sparta.and.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.and.dto.request.ContestBoardRequestDto;
import com.sparta.and.dto.response.ContestBoardResponseDto;
import com.sparta.and.entity.ContestBoard;
import com.sparta.and.repository.ContestBoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContestBoardService {

	private final ContestBoardRepository contestBoardRepository;

	public List<ContestBoardResponseDto> getContests() {
		return contestBoardRepository.findAllByOrderByCreatedDateDesc().stream().map(ContestBoardResponseDto::new).toList();
	}

	public ContestBoardResponseDto createContest (ContestBoardRequestDto requestDto){
		ContestBoard createContest = new ContestBoard(requestDto);
		contestBoardRepository.save(createContest);

		return new ContestBoardResponseDto(createContest);
	}


}
