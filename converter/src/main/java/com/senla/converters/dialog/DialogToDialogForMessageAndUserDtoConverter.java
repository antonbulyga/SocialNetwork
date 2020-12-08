package com.senla.converters.dialog;

import com.senla.dto.dialog.DialogForMessageAndUserDto;
import com.senla.entity.Dialog;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DialogToDialogForMessageAndUserDtoConverter implements Converter<Dialog, DialogForMessageAndUserDto> {

    @Override
    public DialogForMessageAndUserDto convert(Dialog dialog) {
        return DialogForMessageAndUserDto.builder()
                .id(dialog.getId())
                .name(dialog.getName())
                .timeCreation(dialog.getTimeCreation())
                .build();
    }
}
