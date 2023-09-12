package com.sparta.and.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sparta.and.dto.request.ContestRequestDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@Table(name = "contest")
@NoArgsConstructor
@DynamicInsert
public class Contest extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contest_id")
	private Long id;

	@Column(name = "category")
	private Long category;

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "company", nullable = false)
	private String company;

	@Column(name = "endDate", nullable = false)
	private LocalDateTime endDate;

	@Column(name = "startDate")
	private LocalDateTime startDate;

	@Column(name = "status")
	private ContestStatus status;

	@Column(name = "homepage", nullable = false)
	private String homepage;

	@Column(columnDefinition = "LONGTEXT", nullable = false)
	private String contents;

	@Column(name = "contestViews")
	private Long contestViews = 0L;

	@OneToMany(mappedBy = "contest")
	private List<Contest_BottomCategory> bottomCategories = new ArrayList<>();

	// 북마크 카운트
	@Column
	private Long bookmarkCnt;

	// 북마크
	@OneToMany(mappedBy = "contest", cascade = CascadeType.REMOVE)
	private List<Bookmark> bookmarkList = new ArrayList<>();


	public Contest(ContestRequestDto requestDto) {
		this.author = requestDto.getAuthor();
		this.title = requestDto.getTitle();
		this.company = requestDto.getCompany();
		this.endDate = requestDto.getEndDate();
		this.startDate = requestDto.getStartDate();
		this.status = (ContestStatus.CLOSED);
		this.homepage = requestDto.getHomepage();
		this.contents = requestDto.getContents();
	}

	// 값 입력이 없다면 default => 1번
	@PrePersist
	public void prePersist() {
		this.category = this.category == null ? 1 : this.category;
//		this.contestViews = this.contestViews == null ? 0 : this.contestViews;
	}
}