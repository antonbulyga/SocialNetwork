package com.senla.service.message;

import com.senla.entity.Message;

import java.util.List;

public interface MessageService {
    Message addMessage(Message message);

    void deleteMessage(Long id);

    List<Message> getAllMessages();

    List<Message> getMessagesByDialog_Id(Long dialogId);

    Message getMessage(Long id);

    Message updateMessage(Message message);

    List<Message> getMessageByUser_Id(Long userId);
}
