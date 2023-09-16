package com.sparta.and.service;

import com.sparta.and.dto.SearchRequestDto;
import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.entity.Contest;
import com.sparta.and.repository.ContestRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContestService {

	private final ContestRepository contestRepository;

	public Page<ContestResponseDto> getContests(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Contest> contests = contestRepository.findAll(pageable);
		return contests.map(ContestResponseDto::new);
	}

	public ContestResponseDto getContest(Long contestId) {
		Contest contest = findContest(contestId);

		return new ContestResponseDto(contest);
	}

	public List<ContestResponseDto> searchContest(SearchRequestDto requestDto) {
		// if (requestDto.getKeyword().isBlank()) {
		// 	return getContests();
		// } // 프론트엔드에서 키워드를 입력하지 않고 선택할 경우, 공모전 페이지 반환하도록 연결하는 방향성 검토

		List<ContestResponseDto> list = contestRepository.findByTitleContaining(
				requestDto.getKeyword()).stream().map(ContestResponseDto::new).toList();

		if (list.isEmpty()) {
			throw new IllegalArgumentException("해당 검색결과가 없습니다.");
		}

		return list;
	}

	// public Page<ContestResponseDto> searchContest(SearchRequestDto requestDto, int page, int size) {
	// 	if (requestDto.getKeyword().isBlank()) {
	// 		return getContests(page, size);
	// 	}
	//
	// 	Pageable pageable = PageRequest.of(page, size);
	// 	Page<Contest> contests = contestRepository.findByTitleContaining(
	// 		requestDto.getKeyword(), pageable);
	//
	// 	if (contests.isEmpty()) {
	// 		throw new IllegalArgumentException("해당 검색결과가 없습니다.");
	// 	}
	//
	// 	return contests.map(ContestResponseDto::new);
	// }

	public Contest findContest(Long contestId) {
		return contestRepository.findById(contestId).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 글입니다.")
		);
	}
}
