package com.sparta.and.service;

import com.sparta.and.dto.request.ContestRequestDto;
import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.entity.Contest;
import com.sparta.and.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContestService {

	private final ContestRepository contestRepository;

	public List<ContestResponseDto> getContests() {
		return contestRepository.findAllByOrderByCreatedDateDesc().stream().map(ContestResponseDto::new).toList();
	}

	public ContestResponseDto createContest(ContestRequestDto requestDto) {
		Contest createContest = new Contest(requestDto);
		contestRepository.save(createContest);

		return new ContestResponseDto(createContest);
	}
}
