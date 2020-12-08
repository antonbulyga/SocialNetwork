package com.senla.converters.message;

import com.senla.dto.message.MessageForListDto;
import com.senla.entity.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToMessageForListDtoConverter implements Converter<Message, MessageForListDto> {

    @Override
    public MessageForListDto convert(Message message) {
        return MessageForListDto.builder()
                .id(message.getId())
                .message(message.getMessage())
                .creationTime(message.getCreationTime())
                .build();
    }
}
