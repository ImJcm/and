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

	@Column(name = "email")
	private String userEmail;
//   private enum role;

	@Column(name = "nickname")
	private String nickname;

	private Long kakaoId;

	private String googleId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CommunityPost> myPostList = new ArrayList<>();

	public User(String userName, String userPassword, String userEmail) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
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

	public User kakaoIdUpdate(Long kakaoId) {
		this.kakaoId = kakaoId;
		return this;
	}

	public User googleIdUpdate(String googleId) {
		this.googleId = googleId;
		return this;
	}

}
