package com.sparta.and.service;

import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.entity.Contest;
import com.sparta.and.entity.ContestStatus;
import com.sparta.and.repository.ContestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
@Slf4j(topic = "ContestService")
@RequiredArgsConstructor
public class ContestService {

	private final ContestRepository contestRepository;

	public List<ContestResponseDto> getContests() {
		return contestRepository.findAllByOrderByCreatedDateDesc().stream().map(ContestResponseDto::new).toList();
	}


//	public List<ContestResponseDto> searchContest(SearchRequestDto requestDto) {
//		if (requestDto.getKeyword().isBlank()) {
//			return getContests();
//		}
//
//		List<ContestResponseDto> list = contestRepository.findByTitleContaining(
//				requestDto.getKeyword()).stream().map(ContestResponseDto::new).toList();
//
//		if (list.isEmpty()) {
//			throw new IllegalArgumentException("해당 검색결과가 없습니다.");
//		}
//
//		return list;
//	}

	@Transactional
	@Scheduled(cron = "0 0 0 * * ?") //매일 자정에 실행됨
	public void updateContestStatus() {
		log.info("Service - updateStatus : 시작");

		contestRepository.findAll() //모든 공보전을 가져오고
				.forEach(contest -> {  // 반복 루프 ContestStatus메서드 호출해서 상태코드 설정
					ContestStatus status = contestStatus(contest);
					contest.setStatus(status); //contest 객체에 set
					log.info("상태코드 update확인 ID {}: {}", contest.getId(), status);
				});

		log.info("Service - updateStatus : 끝");
	}

	public ContestStatus contestStatus(Contest contest) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime startDate = contest.getStartDate();
		LocalDateTime endDate = contest.getEndDate();
		LocalDateTime endDateMinus3Days = endDate.minusDays(3);

		if (currentDateTime.isBefore(startDate)) {
			return ContestStatus.UPCOMING;  //접수예정
		} else if (currentDateTime.isEqual(startDate) || currentDateTime.isBefore(endDate)) {
			return ContestStatus.ONGOING; //접수중
		} else if (currentDateTime.isBefore(endDateMinus3Days)) {
			return ContestStatus.CLOSING; //마감임박
		} else {
			return ContestStatus.CLOSED; //마감
		}
	}
}

