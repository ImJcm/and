package com.sparta.and.service;

import com.sparta.and.dto.request.S3FileDto;
import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.entity.Contest;
import com.sparta.and.entity.S3File;
import com.sparta.and.repository.ContestRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContestService {

	private final ContestRepository contestRepository;
	private final EntityManager entityManager;

	public Page<ContestResponseDto> getContests(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Contest> contests = contestRepository.findAll(pageable);

		return contests.map(contest -> {
			List<S3FileDto> s3Files = getContestImagesUsingNativeQuery(contest.getId()); // Fetch associated images
			return new ContestResponseDto(contest, s3Files);
		});
	}

	public ContestResponseDto getContest(Long contestId) {
		Contest contest = findContest(contestId);
		List<S3FileDto> s3Files = getContestImagesUsingNativeQuery(contestId); // Fetch associated images
		return new ContestResponseDto(contest, s3Files);
	}

	public List<S3FileDto> getContestImagesUsingNativeQuery(Long contestId) {
		String nativeQuery = "SELECT * FROM s3files WHERE contest_id = :contestId";
		List<S3File> s3Files = entityManager.createNativeQuery(nativeQuery, S3File.class)
				.setParameter("contestId", contestId)
				.getResultList();

		List<S3FileDto> s3FileDtos = s3Files.stream()
				.map(S3FileDto::new)
				.collect(Collectors.toList());
		return s3FileDtos;
	}

	public Contest findContest(Long contestId) {
		return contestRepository.findById(contestId).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 글입니다.")
		);
	}
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


//	public List<ContestResponseDto> searchContest(SearchRequestDto requestDto) {
//		// if (requestDto.getKeyword().isBlank()) {
//		// 	return getContests();
//		// } // 프론트엔드에서 키워드를 입력하지 않고 선택할 경우, 공모전 페이지 반환하도록 연결하는 방향성 검토
//
//		List<ContestResponseDto> list = contestRepository.findByTitleContaining(
//				requestDto.getKeyword()).stream().map(ContestResponseDto::new).toList();
//
//		if (list.isEmpty()) {
//			throw new IllegalArgumentException("해당 검색결과가 없습니다.");
//		}
//
//		return list;