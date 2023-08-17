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

    private Long kakaoId;

    private String googleId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CommunityPost> myPostList = new ArrayList<>();
}
