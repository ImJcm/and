package com.sparta.and.dto.response;

import com.sparta.and.dto.request.S3FileDto;
import com.sparta.and.entity.Contest;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter

public class ContestResponseDto {
	private Long contestId;
	private String target;
	private String title;
	private String company;
	private LocalDateTime endDate;
	private LocalDateTime startDate;
	private String status;
	private String homepage;
	private String contents;
	private String prize;
	private Long contestViews;
	private List<S3FileDto> s3Files;

	public ContestResponseDto(Contest contest, List<S3FileDto> s3Files) {
		this.contestId = contest.getId();
		this.target = contest.getTarget();
		this.title = contest.getTitle();
		this.company = contest.getCompany();
		this.endDate = contest.getEndDate();
		this.startDate = contest.getStartDate();
		this.status = contest.getStatus().getDisplayText();
		this.homepage = contest.getHomepage();
		this.contents = contest.getContents();
		this.contestViews = contest.getContestViews();
		this.prize = contest.getPrize();
		this.s3Files = s3Files; // Set the list of associated images
	}
}