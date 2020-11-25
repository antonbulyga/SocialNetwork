package com.senla.controller;

import com.senla.converters.MessageDtoToMessage;
import com.senla.converters.MessageToMessageDto;
import com.senla.dto.MessageDto;
import com.senla.entity.Dialog;
import com.senla.entity.Message;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.exception.RestError;
import com.senla.service.MessageService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/messages/")
@Slf4j
public class MessageController {

    private MessageService messageService;
    private MessageToMessageDto messageToMessageDto;
    private MessageDtoToMessage messageDtoToMessage;
    private UserService userService;

    @Autowired
    public MessageController(MessageService messageService, MessageToMessageDto messageToMessageDto, MessageDtoToMessage messageDtoToMessage, UserService userService) {
        this.messageService = messageService;
        this.messageToMessageDto = messageToMessageDto;
        this.messageDtoToMessage = messageDtoToMessage;
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<MessageDto>> getAllMessagesForUserFromAllDialogs(){
        List<Message> fullMessageList = new ArrayList<>();
        List<MessageDto> messageDtoList = new ArrayList<>();
        User user = userService.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();

        for (Dialog d : dialogs) {
            List<Message> messageList = messageService.getMessagesByDialog_Id(d.getId());
            fullMessageList.addAll(messageList);
        }

        for (int i = 0; i < fullMessageList.size(); i++) {
            MessageDto result = messageToMessageDto.convert(fullMessageList.get(i));
            messageDtoList.add(result);
        }
        return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<MessageDto> addMessageToDialog(@RequestBody MessageDto messageDto) {
        Message message = messageDtoToMessage.convert(messageDto);
        User user = userService.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        Dialog dialog = message.getDialog();
        if(dialog == null){
            log.error("Trying to add message with null dialog");
            throw new RestError("Trying to add message with null dialog");
        }
        for (Dialog d : dialogs){
            if(d.getId() == dialog.getId()){
                messageService.addMessage(message);
                log.info("Adding message to the dialog");
            }
        }

        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteMessage(@RequestParam (name = "id") long id) {
        messageService.getMessage(id);
        User user = userService.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        for(Message m : messages){
            if(m.getId() == id){
                messageService.deleteMessage(id);
                return ResponseEntity.ok()
                        .body("You have deleted message successfully");
            }
        }

        return ResponseEntity.ok()
                .body("User has no message with this id");
    }

    @PostMapping(value = "update")
    public ResponseEntity<MessageDto> updateMessage(@RequestBody MessageDto messageDto) {
        Message message = messageDtoToMessage.convert(messageDto);
        User user = userService.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        messageService.getMessage(message.getId());
        for(Message m : messages){
            if(m.getId() == message.getId()){
                messageService.editMessage(message);
            }
        }

        messageService.editMessage(message);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/dialog/{id}")
    public ResponseEntity<List<MessageDto>> getMessagesByDialog_Id(@PathVariable (name = "id") long id) {
        List<Message> messages = null;
        User user = userService.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        List<Message> messageList = messageService.getMessagesByDialog_Id(id);
        if(messageList.size() == 0){
            throw new EntityNotFoundException("Dialog does not exist");
        }
        for(Dialog d : dialogs){
            if(d.getId() == id){
                 messages = messageService.getMessagesByDialog_Id(id);
                 List<MessageDto> messageDtoList = messages.stream().map(message -> messageToMessageDto.convert(message)).collect(Collectors.toList());
                 return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
            }
            else {
                log.error("You are trying to get messages from someone else dialog");
                throw new RestError("You are trying to get messages from someone else dialog");
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable(name = "id") Long messageId) {
        MessageDto messageDto = null;
        Message message = messageService.getMessage(messageId);
        User user = userService.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        for(Message m : messages){
            if(m.getId() == message.getId()){
                messageDto = messageToMessageDto.convert(message);
            }
        }
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
}
