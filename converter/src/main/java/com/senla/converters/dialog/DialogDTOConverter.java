package com.senla.converters.dialog;

import com.senla.converters.message.MessageToMessageForListDtoConverter;
import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.dto.dialog.DialogDto;
import com.senla.entity.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DialogDTOConverter implements Converter<Dialog, DialogDto> {

    private final MessageToMessageForListDtoConverter messageToMessageForListDtoConverter;
    private final UserToUserNestedDtoConverter userToUserNestedDtoConverter;

    @Autowired
    public DialogDTOConverter(MessageToMessageForListDtoConverter messageToMessageForListDtoConverter, UserToUserNestedDtoConverter userToUserNestedDtoConverter) {
        this.messageToMessageForListDtoConverter = messageToMessageForListDtoConverter;
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
    }

    @Override
    public DialogDto convert(Dialog dialog) {
        return DialogDto.builder()
                .id(dialog.getId())
                .name(dialog.getName())
                .messages(dialog.getMessages().stream().map(messageToMessageForListDtoConverter::convert).collect(Collectors.toList()))
                .timeCreation(dialog.getTimeCreation())
                .userList(dialog.getUserList().stream().map(userToUserNestedDtoConverter::convert).collect(Collectors.toList()))
                .build();
    }
}
