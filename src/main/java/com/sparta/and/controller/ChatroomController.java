package com.sparta.and.controller;

import com.sparta.and.dto.chat.ChatroomRequestDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatroomController {
    private final ChatroomService chatroomService;

    @GetMapping("/rooms")
    public ModelAndView getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ModelAndView mv = new ModelAndView("rooms");

        mv.addObject("list", chatroomService.getChatRooms(userDetails));

        return mv;
    }

    @PostMapping("/room")
    public String createRoom(@RequestBody ChatroomRequestDto chatroomDto, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes rttr) {
        rttr.addFlashAttribute("room", chatroomService.createChatroom(chatroomDto,userDetails));
        return "redirect:/api/chat/rooms";
    }

    @GetMapping("/room")
    //public void getRoom(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
    public void getRoom(Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("room", chatroomService.getChatRoom(roomId, userDetails));
    }
}
