package com.senla.facade;

import com.senla.converters.DialogDtoToDialog;
import com.senla.converters.DialogToDialogDto;
import com.senla.dto.DialogDto;
import com.senla.entity.Dialog;
import com.senla.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DialogFacade {

    private final DialogService dialogService;
    private final DialogToDialogDto dialogToDialogDto;
    private final DialogDtoToDialog dialogDtoToDialog;

    @Autowired
    public DialogFacade(DialogService dialogService, DialogToDialogDto dialogToDialogDto, DialogDtoToDialog dialogDtoToDialog) {
        this.dialogService = dialogService;
        this.dialogToDialogDto = dialogToDialogDto;
        this.dialogDtoToDialog = dialogDtoToDialog;
    }

    public DialogDto addDialog(DialogDto dialogDto){
        dialogService.addDialog(dialogDtoToDialog.convert(dialogDto));
        return dialogDto;
    }

    public void deleteDialog(long id){
        dialogService.deleteDialog(id);
    }

    public DialogDto updateDialog(DialogDto dialogDto){
        Dialog dialog = dialogDtoToDialog.convert(dialogDto);
        dialogService.updateDialog(dialog);
        return dialogDto;
    }

    public List<DialogDto> getAllDialogs(){
        List<Dialog> messages = dialogService.getAll();
        return messages.stream().map(p -> dialogToDialogDto.convert(p)).collect(Collectors.toList());
    }

    public DialogDto getDialogDto(Long id){
        return dialogToDialogDto.convert(dialogService.getDialog(id));
    }

    public Dialog getDialog(Long id){
        return dialogService.getDialog(id);
    }

    public DialogDto getDialogByName(String name){
        Dialog dialog = dialogService.getDialogByName(name);
        return dialogToDialogDto.convert(dialog);
    }

    public DialogDto addUserToDialog(Long dialogId,Long userId){
       Dialog dialog = dialogService.addUserToDialog(dialogId, userId);
       return dialogToDialogDto.convert(dialog);
    }

    public DialogDto deleteUserFromDialog(Long dialogId,Long userId){
        Dialog dialog = dialogService.deleteUserFromDialog(dialogId, userId);
        return dialogToDialogDto.convert(dialog);
    }

    public List<DialogDto> convertDialogListToLikeDto(List<Dialog> dialogs){
        return dialogs.stream().map(d -> dialogToDialogDto.convert(d)).collect(Collectors.toList());
    }
}
