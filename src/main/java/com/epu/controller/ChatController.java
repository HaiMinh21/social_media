package com.epu.controller;

import com.epu.models.Chat;
import com.epu.models.User;
import com.epu.request.CreateChatRequest;
import com.epu.service.ChatService;
import com.epu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/chats")
    public Chat createChat(@RequestHeader("Authorization") String jwt, @RequestBody CreateChatRequest req) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        User user2 = userService.findUserById(req.getUserId());
        Chat chat = chatService.createChat(reqUser, user2);
        return chat;
    }

    @GetMapping("/api/chats")
    public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt){
        User user = userService.findUserByJwt(jwt);
        List<Chat> chats = chatService.findUsersChat(user.getId());
        return chats;
    }
}
