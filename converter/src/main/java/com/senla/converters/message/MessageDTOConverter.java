
package com.senla.converters.message;

import com.senla.converters.user.UserToUserNestedDtoConverter;
import com.senla.converters.dialog.DialogToDialogForMessageAndUserDtoConverter;
import com.senla.dto.message.MessageDto;
import com.senla.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageDTOConverter implements Converter<Message, MessageDto> {

    private UserToUserNestedDtoConverter userToUserNestedDtoConverter;
    private DialogToDialogForMessageAndUserDtoConverter dialogToDialogForMessageAndUserDtoConverter;

    @Autowired
    public MessageDTOConverter(UserToUserNestedDtoConverter userToUserNestedDtoConverter, DialogToDialogForMessageAndUserDtoConverter dialogToDialogForMessageAndUserDtoConverter) {
        this.userToUserNestedDtoConverter = userToUserNestedDtoConverter;
        this.dialogToDialogForMessageAndUserDtoConverter = dialogToDialogForMessageAndUserDtoConverter;
    }

    @Override
    public MessageDto convert(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .user(userToUserNestedDtoConverter.convert(message.getUser()))
                .creationTime(message.getCreationTime())
                .message(message.getMessage())
                .dialog(dialogToDialogForMessageAndUserDtoConverter.convert(message.getDialog()))
                .build();
    }
}

