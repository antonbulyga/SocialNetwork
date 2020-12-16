package com.senla.facade;

import com.senla.converters.dialog.ReverseDialogDTOConverter;
import com.senla.converters.dialog.DialogDTOConverter;
import com.senla.dto.dialog.DialogDto;
import com.senla.entity.Dialog;
import com.senla.service.dialog.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DialogFacade {

    private final DialogService dialogService;
    private final DialogDTOConverter dialogDTOConverter;
    private final ReverseDialogDTOConverter reverseDialogDTOConverter;

    @Autowired
    public DialogFacade(DialogService dialogService, DialogDTOConverter dialogDTOConverter, ReverseDialogDTOConverter reverseDialogDTOConverter) {
        this.dialogService = dialogService;
        this.dialogDTOConverter = dialogDTOConverter;
        this.reverseDialogDTOConverter = reverseDialogDTOConverter;
    }

    public DialogDto addDialog(DialogDto dialogDto) {
        Dialog dialog = dialogService.addDialog(reverseDialogDTOConverter.convert(dialogDto));
        return dialogDTOConverter.convert(dialog);
    }

    public void deleteDialog(long id) {
        dialogService.deleteDialog(id);
    }

    public DialogDto updateDialog(DialogDto dialogDto) {
        Dialog dialog = reverseDialogDTOConverter.convert(dialogDto);
        dialogService.updateDialog(dialog);
        return dialogDTOConverter.convert(dialog);
    }

    public List<DialogDto> getAllDialogs() {
        List<Dialog> messages = dialogService.getAllDialogs();
        return messages.stream().map(dialogDTOConverter::convert).collect(Collectors.toList());
    }

    public DialogDto getDialogDto(Long id) {
        return dialogDTOConverter.convert(dialogService.getDialog(id));
    }

    public Dialog getDialog(Long id) {
        return dialogService.getDialog(id);
    }

    public DialogDto getDialogByName(String name) {
        Dialog dialog = dialogService.getDialogByName(name);
        return dialogDTOConverter.convert(dialog);
    }

    public DialogDto addUserToDialog(Long dialogId, Long userId) {
        Dialog dialog = dialogService.addUserToDialog(dialogId, userId);
        return dialogDTOConverter.convert(dialog);
    }

    public DialogDto deleteUserFromDialog(Long dialogId, Long userId) {
        Dialog dialog = dialogService.deleteUserFromDialog(dialogId, userId);
        return dialogDTOConverter.convert(dialog);
    }

    public List<DialogDto> convertDialogListToLikeDto(List<Dialog> dialogs) {
        return dialogs.stream().map(dialogDTOConverter::convert).collect(Collectors.toList());
    }
}
