package com.senla.model.converters;

import com.senla.model.dto.MessageShortDto;
import com.senla.model.entity.Message;
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
