package com.study.Chat.Controller;

import com.study.Chat.DTO.ChatRoomDTO;
import com.study.Chat.Service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class ChatRoomController {

    @Autowired
    private ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/")
    public String goChatRoom(Model model){

        model.addAttribute("list", chatService.findAllRoom());
//        model.addAttribute("user", "hey");
        log.info("SHOW ALL ChatList {}", chatService.findAllRoom());
        return "roomlist";
    }

    // 채팅방 생성
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr) {
        ChatRoomDTO room = chatService.createChatRoom(name);
        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    // 채팅방 입장 화면
    // @PathVariable : url 에 넘어오는 변수를 매개변수로 받을 수 있게 하는 어노테이션
    // {roomId} 가 url 변수 -> String 타입 roomId 로 받게됨
    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId){

        log.info("roomId {}", roomId);
        model.addAttribute("room", chatService.findRoomById(roomId));
        return "chatroom";
    }

}