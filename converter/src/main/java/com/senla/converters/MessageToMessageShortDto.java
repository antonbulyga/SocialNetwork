package com.senla.converters;

import com.senla.dto.MessageShortDto;
import com.senla.entity.Message;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MessageToMessageShortDto implements Converter<Message, MessageShortDto> {

    @Override
    public MessageShortDto convert(Message message) {
        return MessageShortDto.builder()
                .id(message.getId())
                .message(message.getMessage())
                .creationTime(message.getCreationTime())
                .build();
    }
}
