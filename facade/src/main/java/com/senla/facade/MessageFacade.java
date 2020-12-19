package com.senla.facade;

import com.senla.converters.message.ReverseMessageDTOConverter;
import com.senla.converters.message.MessageDTOConverter;
import com.senla.dto.message.MessageDto;
import com.senla.entity.Message;
import com.senla.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageFacade {

    private final MessageService messageService;
    private final MessageDTOConverter messageDTOConverter;
    private final ReverseMessageDTOConverter reverseMessageDTOConverter;

    @Autowired
    public MessageFacade(MessageService messageService, MessageDTOConverter messageDTOConverter, ReverseMessageDTOConverter reverseMessageDTOConverter) {
        this.messageService = messageService;
        this.messageDTOConverter = messageDTOConverter;
        this.reverseMessageDTOConverter = reverseMessageDTOConverter;
    }

    public MessageDto addMessage(MessageDto messageDto) {
        Message message = messageService.addMessage(reverseMessageDTOConverter.convert(messageDto));
        return messageDTOConverter.convert(message);
    }

    public void deleteMessage(long id) {
        messageService.deleteMessage(id);
    }

    public MessageDto updateMessage(MessageDto messageDto) {
        Message message = messageService.updateMessage(reverseMessageDTOConverter.convert(messageDto));
        return messageDTOConverter.convert(message);
    }

    public List<MessageDto> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return messages.stream().map(messageDTOConverter::convert).collect(Collectors.toList());
    }

    public MessageDto getMessageDto(Long id) {
        return messageDTOConverter.convert(messageService.getMessage(id));
    }

    public Message getMessage(Long id) {
        return messageService.getMessage(id);
    }

    public List<MessageDto> getMessagesByDialog_Id(Long dialogId) {
        List<Message> messages = messageService.getMessagesByDialog_Id(dialogId);
        return messages.stream().map(messageDTOConverter::convert).collect(Collectors.toList());
    }

    public List<MessageDto> getMessageByUser_Id(Long userId) {
        List<Message> messages = messageService.getMessageByUser_Id(userId);
        return messages.stream().map(messageDTOConverter::convert).collect(Collectors.toList());
    }

}
