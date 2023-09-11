package com.sparta.and.service;

import java.util.List;

import com.sparta.and.dto.request.ContestRequestDto;
import com.sparta.and.entity.Contest;
import org.springframework.stereotype.Service;

import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.repository.ContestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ContestService {

	private final ContestRepository contestRepository;

	public List<ContestResponseDto> getContests() {
		return contestRepository.findAllByOrderByCreatedDateDesc().stream().map(ContestResponseDto::new).toList();
	}

	public ContestResponseDto createContest (ContestRequestDto requestDto){
		Contest createContest = new Contest(requestDto);
		contestRepository.save(createContest);

		return new ContestResponseDto(createContest);
	}


}
