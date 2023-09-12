package com.sparta.and.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {
	@Value("${auth.kakao.client_id}")
	private String kakaoClientKey;

	@Value("${auth.kakao.redirectURL}")
	private String kakaoRedirectURL;

	@GetMapping("/main")
	public String mainPage() {
		return "main";
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("kakaoClientKey", kakaoClientKey);
		model.addAttribute("kakaoRedirectURL", kakaoRedirectURL);
		return "login";
	}

	@GetMapping("/posting")
	public String postPage() {
		return "postModal";
	}

	@GetMapping("/onepost/{postId}")
	public String getOnePost(@PathVariable Long postId, Model model) {
		model.addAttribute("postId", postId);

		return "onepost";
	}
}