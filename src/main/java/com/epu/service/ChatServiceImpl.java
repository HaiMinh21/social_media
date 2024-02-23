package com.epu.service;

import com.epu.models.Chat;
import com.epu.models.User;
import com.epu.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExist = chatRepository.findChatByUsersId(reqUser, user2);

        if (isExist != null){
            return isExist;
        }
        Chat chat = new Chat();
        chat.getUsers().add(user2);
        chat.getUsers().add(reqUser);
        chat.setTimestamp(LocalDateTime.now());

        return chatRepository.save(chat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws Exception {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isEmpty()) {
            throw new Exception("không tìm thấy trò chuyện với id " + chatId);
        }
        return chat.get();
    }

    @Override
    public List<Chat> findUsersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }
}
