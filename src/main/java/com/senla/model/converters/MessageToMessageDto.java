
package com.senla.model.converters;

import com.senla.model.dto.MessageDto;
import com.senla.model.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToMessageDto implements Converter<Message, MessageDto> {
    private UserToUserShortDto userToUserShortDto;
    private DialogToDialogShortDto dialogToDialogShortDto;

    @Autowired
    public MessageToMessageDto(UserToUserShortDto userToUserShortDto, DialogToDialogShortDto dialogToDialogShortDto) {
        this.userToUserShortDto = userToUserShortDto;
        this.dialogToDialogShortDto = dialogToDialogShortDto;
    }

    @Override
    public MessageDto convert(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .user(userToUserShortDto.convert(message.getUser()))
                .creationTime(message.getCreationTime())
                .message(message.getMessage())
                .dialog(dialogToDialogShortDto.convert(message.getDialog()))
                .build();
    }
}

