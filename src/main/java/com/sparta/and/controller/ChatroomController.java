package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.chat.ChatHistoryResponseDto;
import com.sparta.and.dto.chat.ChatroomRequestDto;
import com.sparta.and.dto.chat.ChatroomResponseDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.ChatHistoryService;
import com.sparta.and.service.ChatroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatroomController {
    private final ChatroomService chatroomService;
    private final ChatHistoryService chatHistoryService;

    /*@GetMapping("/rooms")
    public ModelAndView getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        ModelAndView mv = new ModelAndView("rooms");
        mv.addObject("list", chatroomService.getChatRooms(userDetails));
        return mv;
    }

    @PostMapping("/room")
    public String createRoom(@ModelAttribute ChatroomRequestDto chatroomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes rttr) {
        rttr.addFlashAttribute("room", chatroomService.createChatroom(chatroomRequestDto,userDetails));
        return "redirect:/api/chat/rooms";
    }

    @GetMapping("/room")
    public ModelAndView getRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        model.addAttribute("room", chatroomService.getChatRoom(roomId, userDetails));
        model.addAttribute("chatHistory",chatHistoryService.getChatHistorys(roomId));
        return "room";
    }
    */


    @GetMapping("/rooms")
    public ResponseEntity<List<ChatroomResponseDto>> getRooms(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(chatroomService.getChatRooms(userDetails));
    }

    @PostMapping("/room")
    public ResponseEntity<ChatroomResponseDto> createRoom(@ModelAttribute ChatroomRequestDto chatroomRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, RedirectAttributes rttr) {
        return ResponseEntity.ok().body(chatroomService.createChatroom(chatroomRequestDto,userDetails));
    }

    @GetMapping("/room")
    public ResponseEntity<ChatroomResponseDto> getRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        return ResponseEntity.ok().body(chatroomService.getChatRoom(roomId, userDetails));

    }

    @GetMapping("/room/chatting")
    public ResponseEntity<List<ChatHistoryResponseDto>> getChatting(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(chatHistoryService.getChatHistorys(roomId));
    }


    @DeleteMapping("/room")
    public ResponseEntity<ApiResponseDto> deleteRoom(@RequestParam Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(chatroomService.deleteRoom(roomId, userDetails));
    }
}
