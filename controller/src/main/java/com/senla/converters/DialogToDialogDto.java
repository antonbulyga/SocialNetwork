package com.senla.converters;

import com.senla.dto.DialogDto;
import com.senla.entity.Dialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DialogToDialogDto implements Converter<Dialog, DialogDto> {
    private MessageToMessageShortDto messageToMessageShortDto;
    private UserToUserShortDto userToUserShortDto;

    @Autowired
    public DialogToDialogDto(MessageToMessageShortDto messageToMessageShortDto, UserToUserShortDto userToUserShortDto) {
        this.messageToMessageShortDto = messageToMessageShortDto;
        this.userToUserShortDto = userToUserShortDto;
    }

    @Override
    public DialogDto convert(Dialog dialog) {
        return DialogDto.builder()
                .id(dialog.getId())
                .name(dialog.getName())
                .messages(dialog.getMessages().stream().map(m -> messageToMessageShortDto.convert(m)).collect(Collectors.toList()))
                .timeCreation(dialog.getTimeCreation())
                .userList(dialog.getUserList().stream().map(u -> userToUserShortDto.convert(u)).collect(Collectors.toList()))
                .build();
    }
}
