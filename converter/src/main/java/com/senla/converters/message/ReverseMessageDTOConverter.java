package com.senla.converters.message;

import com.senla.dto.message.MessageDto;
import com.senla.entity.Message;
import com.senla.service.dialog.DialogService;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReverseMessageDTOConverter implements Converter<MessageDto, Message> {

    private final UserService userService;
    private final DialogService dialogService;

    @Autowired
    public ReverseMessageDTOConverter(UserService userService, DialogService dialogService) {
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
                .creationTime(LocalDateTime.now())
                .build();
    }
}
