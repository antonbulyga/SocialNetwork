package com.senla.controller;

import com.senla.dto.MessageDto;
import com.senla.entity.Dialog;
import com.senla.entity.Message;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.exception.RestError;
import com.senla.facade.MessageFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/messages/")
@Slf4j
public class MessageController {

    private final UserFacade userFacade;
    private final MessageFacade messageFacade;

    @Autowired
    public MessageController(UserFacade userFacade, MessageFacade messageFacade) {
        this.userFacade = userFacade;
        this.messageFacade = messageFacade;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<MessageDto>> getAllMessagesForUserFromAllDialogs(){
        List<MessageDto> fullMessageDtoList = new ArrayList<>();
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();

        for (Dialog d : dialogs) {
            List<MessageDto> messageDtoList = messageFacade.getMessagesByDialog_Id(d.getId());
            fullMessageDtoList.addAll(messageDtoList);
        }
        return new ResponseEntity<>(fullMessageDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<MessageDto> addMessageToDialog(@RequestBody MessageDto messageDto) {
        Message message = messageFacade.getLike(messageDto.getId());
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        Dialog dialog = message.getDialog();
        if(dialog == null){
            log.error("Trying to add message with null dialog");
            throw new RestError("Trying to add message with null dialog");
        }
        for (Dialog d : dialogs){
            if(d.getId() == dialog.getId()){
                messageFacade.addMessage(messageDto);
                log.info("Adding message to the dialog");
                return new ResponseEntity<>(messageDto, HttpStatus.OK);
            }
        }

        log.error("User can not add message to someone else dialog");
        throw new RestError("User can not add message to someone else dialog");
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteMessage(@RequestParam (name = "id") long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        for(Message m : messages){
            if(m.getId() == id){
                messageFacade.deleteMessage(id);
                return ResponseEntity.ok()
                        .body("You have deleted message successfully");
            }
        }

        log.error("User has no message with this id");
        throw new RestError("User has no message with this id");
    }

    @PostMapping(value = "update")
    public ResponseEntity<MessageDto> updateMessage(@RequestBody MessageDto messageDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        messageFacade.getLike(messageDto.getId());
        for(Message m : messages){
            if(m.getId() == messageDto.getId()){
                messageFacade.updateMessage(messageDto);
                log.info("You have updated message successfully");
                return new ResponseEntity<>(messageDto, HttpStatus.OK);
            }

        }
        log.error("You can't edit someone else message");
        throw new RestError("You can't edit someone else message");
    }

    @GetMapping(value = "search/dialog/{id}")
    public ResponseEntity<List<MessageDto>> getMessagesByDialog_Id(@PathVariable (name = "id") long id) {
        List<MessageDto> messageDtoList;
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        List<MessageDto> messageListDto = messageFacade.getMessagesByDialog_Id(id);
        if(messageListDto.size() == 0){
            throw new EntityNotFoundException("Dialog does not exist");
        }
        for(Dialog d : dialogs){
            if(d.getId() == id){
                messageDtoList = messageFacade.getMessagesByDialog_Id(id);
                 return new ResponseEntity<>(messageDtoList, HttpStatus.OK);
            }

        }
        log.error("You are trying to get messages from someone else dialog");
        throw new RestError("You are trying to get messages from someone else dialog");

    }

    @GetMapping(value = "{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable(name = "id") Long messageId) {
        MessageDto messageDto;
        Message message = messageFacade.getLike(messageId);
        User user = userFacade.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        for(Message m : messages){
            if(m.getId() == message.getId()){
               messageDto = messageFacade.getMessageDto(m.getId());
                return new ResponseEntity<>(messageDto, HttpStatus.OK);
            }
        }
        log.error("You are trying to get someone else message");
        throw new RestError("You are trying to get someone else message");
    }
}
