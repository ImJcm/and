package com.sparta.and.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfoDto {
	private String id;
	private String userName;
	private String email;

	public GoogleUserInfoDto(String id, String userName, String email) {
		this.id = id;
		this.userName = userName;
		this.email = email;
	}
}