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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userPassword;
    private String userName;
    private String userEmail;
//   private enum role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<CommunityPost> myPostList = new ArrayList<>();
}
