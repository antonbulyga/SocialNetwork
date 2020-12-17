package com.senla.controller;

import com.senla.dto.message.MessageDto;
import com.senla.entity.Dialog;
import com.senla.entity.Message;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.exception.RestError;
import com.senla.facade.DialogFacade;
import com.senla.facade.MessageFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/messages")
@Slf4j
public class MessageController {

    private final UserFacade userFacade;
    private final MessageFacade messageFacade;
    private final DialogFacade dialogFacade;

    @Autowired
    public MessageController(UserFacade userFacade, MessageFacade messageFacade, DialogFacade dialogFacade) {
        this.userFacade = userFacade;
        this.messageFacade = messageFacade;
        this.dialogFacade = dialogFacade;
    }

    /**
     * Get all messages from all dialogs where user participate
     *
     * @return list of the message dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "")
    public ResponseEntity<List<MessageDto>> getAllMessagesForUserFromAllDialogs() {
        List<MessageDto> fullMessageDtoList = new ArrayList<>();
        userFacade.getUserFromSecurityContext().getDialogs().forEach(d -> fullMessageDtoList.addAll(messageFacade.getMessagesByDialog_Id(d.getId())));
        return new ResponseEntity<>(fullMessageDtoList, HttpStatus.OK);
    }

    /**
     * Add message to the dialog
     *
     * @param messageDto message dto
     * @return message dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/add")
    public ResponseEntity<MessageDto> addMessageToDialog(@Valid @RequestBody MessageDto messageDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        Dialog dialog = dialogFacade.getDialog(messageDto.getDialog().getId());
        if (dialog == null) {
            log.error("Trying to add message with null dialog");
            throw new RestError("Trying to add message with null dialog");
        }
        if (user.getId().equals(messageDto.getUser().getId())) {
            for (Dialog d : dialogs) {
                if (d.getId().equals(dialog.getId())) {
                    MessageDto messageDtoWithTime = messageFacade.addMessage(messageDto);
                    log.info("Adding message to the dialog");
                    return new ResponseEntity<>(messageDtoWithTime, HttpStatus.OK);
                }
            }
        }

        log.error("User can not add message to someone else dialog or user");
        throw new RestError("User can not add message to someone else dialog or user");
    }

    /**
     * Delete message
     *
     * @param id message id
     * @return string response
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteMessage(@RequestParam(name = "id") Long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        for (Message m : messages) {
            if (m.getId().equals(id)) {
                messageFacade.deleteMessage(id);
                return ResponseEntity.ok()
                        .body("You have deleted message successfully");
            }
        }

        log.error("User has no message with this id");
        throw new RestError("User has no message with this id");
    }

    /**
     * Update message
     *
     * @param messageDto message dto
     * @return message dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping(value = "/update")
    public ResponseEntity<MessageDto> updateMessage(@Valid @RequestBody MessageDto messageDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        messageFacade.getMessage(messageDto.getId());
        for (Message m : messages) {
            if (m.getId() == messageDto.getId()) {
                MessageDto messageDtoWithTime = messageFacade.updateMessage(messageDto);
                log.info("You have updated message successfully");
                return new ResponseEntity<>(messageDtoWithTime, HttpStatus.OK);
            }

        }
        log.error("You can't edit someone else message");
        throw new RestError("You can't edit someone else message");
    }

    /**
     * Get message by id
     *
     * @param messageId message id
     * @return message dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable(name = "id") Long messageId) {
        MessageDto messageDto;
        Message message = messageFacade.getMessage(messageId);
        User user = userFacade.getUserFromSecurityContext();
        List<Message> messages = user.getMessages();
        for (Message m : messages) {
            if (m.getId().equals(message.getId())) {
                messageDto = messageFacade.getMessageDto(m.getId());
                return new ResponseEntity<>(messageDto, HttpStatus.OK);
            }
        }
        log.error("You are trying to get someone else message");
        throw new RestError("You are trying to get someone else message");
    }
}
