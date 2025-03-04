package com.sparta.and.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String userPassword;

	@Column(name = "nickname", nullable = false, unique = true)
	private String nickname;

	private Long kakaoId;
	private String googleId;
	private String naverId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> myPostList = new ArrayList<>();

	// 북마크 전체 보기
	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<Bookmark> bookmarkList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<ReportComment> reportCommentList = new ArrayList<>();

	@OneToMany(mappedBy = "founder", cascade = CascadeType.REMOVE)
	private List<Chatroom> foundRoomlist = new ArrayList<>();

	@OneToMany(mappedBy = "participant", cascade = CascadeType.REMOVE)
	private List<Chatroom> partipantRoomlist = new ArrayList<>();

	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public User(String userName, String userPassword, String nickname, Long kakaoId) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.nickname = nickname;
		this.kakaoId = kakaoId;
	}

	public User(String userName, String userPassword, String nickname, String googleId) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.nickname = nickname;
		this.googleId = googleId;
	}

	public User(String naverId) {
		this.naverId = naverId;
	}

	public User kakaoIdUpdate(Long kakaoId) {
		this.kakaoId = kakaoId;
		return this;
	}

	public User googleIdUpdate(String googleId) {
		this.googleId = googleId;
		return this;
	}

	public User naverIdUpdate(String naverId) {
		this.naverId = naverId;
		return this;
	}
}
