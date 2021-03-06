package com.senla.converters.dialog;

import com.senla.dto.dialog.DialogDto;
import com.senla.entity.Dialog;
import com.senla.service.message.MessageService;
import com.senla.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ReverseDialogDTOConverter implements Converter<DialogDto, Dialog> {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ReverseDialogDTOConverter(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public Dialog convert(DialogDto dialogDto) {
        return Dialog.builder()
                .id(dialogDto.getId())
                .name(dialogDto.getName())
                .timeCreation(LocalDateTime.now())
                .messages(dialogDto.getMessages().stream().map(p -> messageService.getMessage(p.getId())).collect(Collectors.toList()))
                .userList(dialogDto.getUserList().stream().map(u -> userService.getUserById(u.getId())).collect(Collectors.toList()))
                .build();
    }
}
