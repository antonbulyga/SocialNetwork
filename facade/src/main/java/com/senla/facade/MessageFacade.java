package com.senla.facade;

import com.senla.converters.MessageDtoToMessage;
import com.senla.converters.MessageToMessageDto;
import com.senla.dto.MessageDto;
import com.senla.entity.Message;
import com.senla.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageFacade {

    private final MessageService messageService;
    private final MessageToMessageDto messageToMessageDto;
    private final MessageDtoToMessage messageDtoToMessage;

    @Autowired
    public MessageFacade(MessageService messageService, MessageToMessageDto messageToMessageDto, MessageDtoToMessage messageDtoToMessage) {
        this.messageService = messageService;
        this.messageToMessageDto = messageToMessageDto;
        this.messageDtoToMessage = messageDtoToMessage;
    }

    public MessageDto addMessage(MessageDto messageDto) {
        Message message = messageService.addMessage(messageDtoToMessage.convert(messageDto));
        MessageDto messageDtoWithDate = messageToMessageDto.convert(message);
        return messageDtoWithDate;
    }

    public void deleteMessage(long id) {
        messageService.deleteMessage(id);
    }

    public MessageDto updateMessage(MessageDto messageDto) {
        Message message = messageService.updateMessage(messageDtoToMessage.convert(messageDto));
        MessageDto messageDtoWithDate = messageToMessageDto.convert(message);
        return messageDtoWithDate;
    }

    public List<MessageDto> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return messages.stream().map(messageToMessageDto::convert).collect(Collectors.toList());
    }

    public MessageDto getMessageDto(Long id) {
        return messageToMessageDto.convert(messageService.getMessage(id));
    }

    public Message getMessage(Long id) {
        return messageService.getMessage(id);
    }

    public List<MessageDto> getMessagesByDialog_Id(Long dialogId) {
        List<Message> messages = messageService.getMessagesByDialog_Id(dialogId);
        return messages.stream().map(messageToMessageDto::convert).collect(Collectors.toList());
    }

}
