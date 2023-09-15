package com.sparta.and.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.and.dto.request.ReportPostRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "reports")
@Entity
public class ReportPost extends TimeStamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_post_id")
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	@Column
	private String reportReason;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public ReportPost(Post post, String reportReason, User reporter) {
		this.post = post;
		this.reportReason = reportReason;
		this.user = reporter;
	}
}
