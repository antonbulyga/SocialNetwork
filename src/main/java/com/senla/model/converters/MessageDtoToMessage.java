package com.senla.model.converters;

import com.senla.model.dto.MessageDto;
import com.senla.model.entity.Message;
import com.senla.model.service.DialogService;
import com.senla.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageDtoToMessage implements Converter<MessageDto, Message> {
    private UserService userService;
    private DialogService dialogService;

    @Autowired
    public MessageDtoToMessage(UserService userService, DialogService dialogService) {
        this.userService = userService;
        this.dialogService = dialogService;
    }

    @Override
    public Message convert(MessageDto messageDto) {
        return Message.builder()
                .id(messageDto.getId())
                .user(userService.getUser(messageDto.getUser().getId()))
                .message(messageDto.getMessage())
                .dialog(dialogService.getDialog(messageDto.getDialog().getId()))
                .creationTime(messageDto.getCreationTime())
                .build();
    }
}
