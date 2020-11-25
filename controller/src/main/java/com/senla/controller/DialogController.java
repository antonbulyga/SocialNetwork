package com.senla.controller;

import com.senla.converters.DialogDtoToDialog;
import com.senla.converters.DialogToDialogDto;
import com.senla.dto.DialogDto;
import com.senla.entity.Dialog;
import com.senla.entity.User;
import com.senla.service.DialogService;
import com.senla.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/dialogs/")
public class DialogController {
    private DialogService dialogService;
    private DialogDtoToDialog dialogDtoToDialog;
    private DialogToDialogDto dialogToDialogDto;
    private UserService userService;

    @Autowired
    public DialogController(DialogService dialogService, DialogDtoToDialog dialogDtoToDialog,
                            DialogToDialogDto dialogToDialogDto, UserService userService) {
        this.dialogService = dialogService;
        this.dialogDtoToDialog = dialogDtoToDialog;
        this.dialogToDialogDto = dialogToDialogDto;
        this.userService = userService;
    }

    @GetMapping(value = "")
        public ResponseEntity<List<DialogDto>> getAllUsersDialogs(){
            User user = userService.getUserFromSecurityContext();
            List<Dialog> dialogList = user.getDialogs();
            List<DialogDto> dialogDtoList = new ArrayList<>();
            for (int i = 0; i < dialogList.size(); i++) {
                DialogDto result = dialogToDialogDto.convert(dialogList.get(i));
                dialogDtoList.add(result);
            }
        return new ResponseEntity<>(dialogDtoList, HttpStatus.OK);
        }

        @PostMapping(value = "add")
        public ResponseEntity<DialogDto> addDialog(@RequestBody DialogDto dialogDto) {
            Dialog dialog = dialogDtoToDialog.convert(dialogDto);
            dialogService.addDialog(dialog);
            return new ResponseEntity<>(dialogDto, HttpStatus.OK);
        }

        @DeleteMapping(value = "delete")
        public ResponseEntity<String> deleteDialog(@RequestParam (name = "id") long id) {
            dialogService.getDialog(id);
            dialogService.delete(id);
            return ResponseEntity.ok()
                    .body("You have deleted dialog successfully");
        }

        @GetMapping(value = "{id}")
        public ResponseEntity<DialogDto> getDialogById(@PathVariable(name = "id") Long id) {
            Dialog dialog = dialogService.getDialog(id);
            DialogDto dialogDto = dialogToDialogDto.convert(dialog);
            return new ResponseEntity<>(dialogDto, HttpStatus.OK);
        }

    @PostMapping(value = "update")
    public ResponseEntity<DialogDto> updateDialog(@RequestBody DialogDto dialogDto) {
        Dialog dialog = dialogDtoToDialog.convert(dialogDto);
        dialogService.editDialog(dialog);
        return new ResponseEntity<>(dialogDto, HttpStatus.OK);
    }

    @GetMapping(value = "search/name")
    public ResponseEntity<DialogDto> getDialogByName(@RequestParam(name = "name") String name) {
        Dialog dialog = dialogService.getDialogByName(name);
        DialogDto dialogDto = dialogToDialogDto.convert(dialog);
        return new ResponseEntity<>(dialogDto, HttpStatus.OK);
    }

    @PostMapping(value = "add/user")
    public ResponseEntity<DialogDto> addUserToDialog(@RequestParam(name = "dialogId") Long dialogId,@RequestParam(name = "userId") Long userId){
        Dialog dialog = dialogService.addUserToDialog(dialogId, userId);
        DialogDto dialogDto = dialogToDialogDto.convert(dialog);
        return new ResponseEntity<>(dialogDto, HttpStatus.OK);
    }

    @PostMapping(value = "delete/user")
    public ResponseEntity<DialogDto> deleteUserFromDialog(@RequestParam(name = "dialogId") Long dialogId,@RequestParam(name = "userId") Long userId){
        Dialog dialog = dialogService.deleteUserFromDialog(dialogId, userId);
        DialogDto dialogDto = dialogToDialogDto.convert(dialog);
        return new ResponseEntity<>(dialogDto, HttpStatus.OK);
    }

}