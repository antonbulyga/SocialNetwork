package com.senla.service;

import com.senla.entity.Message;

import java.util.List;

public interface MessageService {
    Message addMessage(Message message);
    void deleteMessage(long id);
    List<Message> getAllMessages();
    List<Message> getMessagesByDialog_Id(Long dialogId);
    Message getMessage(Long id);
    Message editMessage(Message message);
}
