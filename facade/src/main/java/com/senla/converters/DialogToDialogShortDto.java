package com.senla.converters;

import com.senla.dto.DialogShortDto;
import com.senla.entity.Dialog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DialogToDialogShortDto implements Converter<Dialog, DialogShortDto> {

    @Override
    public DialogShortDto convert(Dialog dialog) {
        return DialogShortDto.builder()
                .id(dialog.getId())
                .name(dialog.getName())
                .timeCreation(dialog.getTimeCreation())
                .build();
    }
}
