package com.epu.service;

import com.epu.models.Chat;
import com.epu.models.Message;
import com.epu.models.User;

import java.util.List;

public interface MessageService {

    public Message createMessage(User user, Integer chatId, Message req) throws Exception;

    public List<Message> findChatsMessages(Integer chatId) throws Exception;
}
