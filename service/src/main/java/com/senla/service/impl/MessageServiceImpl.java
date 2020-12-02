package com.senla.service.impl;

import com.senla.entity.Dialog;
import com.senla.entity.Message;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.MessageRepository;
import com.senla.service.DialogService;
import com.senla.service.MessageService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final DialogService dialogService;
    private final UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, DialogService dialogService, UserService userService) {
        this.messageRepository = messageRepository;
        this.dialogService = dialogService;
        this.userService = userService;
    }

    @Override
    public Message addMessage(Message message) {
        messageRepository.save(message);
        return message;
    }

    @Override
    public void deleteMessage(Long id) {
        Message message = getMessage(id);
        User user = message.getUser();
        user.setMessages(user.getMessages().stream().filter(mess -> !mess.getId().equals(id))
                .collect(Collectors.toList()));
        userService.updateUser(user);
        Dialog dialog = message.getDialog();
        dialog.setMessages(dialog.getMessages().stream().filter(mess -> !mess.getId().equals(id))
                .collect(Collectors.toList()));
        dialogService.updateDialog(dialog);
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> getMessagesByDialog_Id(Long dialogId) {
        return messageRepository.getMessagesByDialog_Id(dialogId);
    }

    @Override
    public Message getMessage(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Message with id = %s is not found", id)));
    }

    @Override
    public Message updateMessage(Message message) {
        messageRepository.save(message);
        return message;
    }
}
