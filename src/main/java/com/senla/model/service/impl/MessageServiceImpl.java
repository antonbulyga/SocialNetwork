package com.senla.model.service.impl;

import com.senla.model.entity.Dialog;
import com.senla.model.entity.Message;
import com.senla.model.entity.User;
import com.senla.model.exception.EntityNotFoundException;
import com.senla.model.repository.MessageRepository;
import com.senla.model.service.DialogService;
import com.senla.model.service.MessageService;
import com.senla.model.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private DialogService dialogService;
    private UserService userService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, DialogService dialogService, UserService userService) {
        this.messageRepository = messageRepository;
        this.dialogService = dialogService;
        this.userService = userService;
    }

    @Override
    public Message addMessage(Message message) {
        Dialog dialog = message.getDialog();
        User user = message.getUser();
        dialogService.addUserToDialog(dialog.getId(), user.getId());
        messageRepository.save(message);
        return message;
    }

    @Override
    public void deleteMessage(long id) {
        Message message = getMessage(id);
        User user = message.getUser();
        List<Message> userMessageList = user.getMessages();
        for (Iterator<Message> iter = userMessageList.iterator(); iter.hasNext();) {
            Message currentMessage = iter.next();
            if(currentMessage.getId() == message.getId()){
                iter.remove();
            }
        }
        user.setMessages(userMessageList);
        userService.editUser(user);
        Dialog dialog = message.getDialog();
        List<Message> messageListInDialog = dialog.getMessages();
        for (Iterator<Message> iter = messageListInDialog.iterator(); iter.hasNext();) {
            Message currentMessage = iter.next();
            if(currentMessage.getId() == message.getId()){
                iter.remove();
            }
        }
        dialog.setMessages(messageListInDialog);
        dialogService.editDialog(dialog);
        messageRepository.deleteById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> messages = messageRepository.findAll();
        return messages;
    }

     @Override
    public List<Message> getMessagesByDialog_Id(Long dialogId) {
        List<Message> messages = messageRepository.getMessagesByDialog_Id(dialogId);
        return messages;
    }

    @Override
    public Message getMessage(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Message with id = %s is not found", id)));
    }

    @Override
    public Message editMessage(Message message) {
        messageRepository.save(message);
        return message;
    }
}
