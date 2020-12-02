package com.senla.controller;

import com.senla.dto.DialogDto;
import com.senla.entity.Dialog;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.DialogFacade;
import com.senla.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/dialogs/")
@Slf4j
public class DialogController {

    private final DialogFacade dialogFacade;
    private final UserFacade userFacade;

    @Autowired
    public DialogController(DialogFacade dialogFacade, UserFacade userFacade) {
        this.dialogFacade = dialogFacade;
        this.userFacade = userFacade;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<DialogDto>> getAllUsersDialogs() {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogList = user.getDialogs();
        log.info("Getting user dialogs");
        List<DialogDto> dialogDtoList = dialogFacade.convertDialogListToLikeDto(dialogList);
        return new ResponseEntity<>(dialogDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "add")
    public ResponseEntity<DialogDto> addDialog(@Valid @RequestBody DialogDto dialogDto) {
        DialogDto dialogDtoWithData = dialogFacade.addDialog(dialogDto);
        log.info("Adding a new dialog");
        return new ResponseEntity<>(dialogDtoWithData, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<String> deleteDialog(@RequestParam(name = "id") long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId() == id) {
                dialogFacade.deleteDialog(id);
                log.info("Deleting the dialog");
                return ResponseEntity.ok()
                        .body("You have deleted dialog successfully");
            }

        }
        log.error("You are trying to delete someone else dialog");
        throw new RestError("You are trying to delete someone else dialog");
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<DialogDto> getDialogById(@PathVariable(name = "id") Long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId().equals(id)) {
                DialogDto dialogDto = dialogFacade.getDialogDto(id);
                return new ResponseEntity<>(dialogDto, HttpStatus.OK);
            }

        }
        log.error("You are trying to get someone else dialog");
        throw new RestError("You are trying to get someone else dialog");
    }

    @PutMapping(value = "update")
    public ResponseEntity<DialogDto> updateDialog(@Valid @RequestBody DialogDto dialogDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId() == dialogDto.getId()) {
                DialogDto dialogDtoWithData = dialogFacade.updateDialog(dialogDto);
                log.error("You are updating dialog");
                return new ResponseEntity<>(dialogDtoWithData, HttpStatus.OK);
            }

        }
        log.error("You are trying to update someone else dialog");
        throw new RestError("You are trying to update someone else dialog");
    }

    @GetMapping(value = "search/name")
    public ResponseEntity<DialogDto> getDialogByName(@RequestParam(name = "name") String name) {
        User user = userFacade.getUserFromSecurityContext();
        DialogDto dialogDto = dialogFacade.getDialogByName(name);
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId() == dialogDto.getId()) {
                log.error("You are getting dialog by name");
                return new ResponseEntity<>(dialogDto, HttpStatus.OK);
            }
        }
        log.error("You are trying to get someone else dialog");
        throw new RestError("You are trying to get someone else dialog");

    }

    @PostMapping(value = "add/user")
    public ResponseEntity<DialogDto> addUserToDialog(@RequestParam(name = "dialogId") Long dialogId, @RequestParam(name = "userId") Long userId) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId().equals(dialogId)) {
                log.error("You are adding user to the dialog");
                DialogDto dialogDtoWithTime = dialogFacade.addUserToDialog(dialogId, userId);
                return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
            }

        }
        log.error("You are trying to add user to the someone else dialog");
        throw new RestError("You are trying to add user to the someone else dialog");

    }

    @DeleteMapping(value = "delete/user")
    public ResponseEntity<DialogDto> deleteUserFromDialog(@RequestParam(name = "dialogId") Long dialogId, @RequestParam(name = "userId") Long userId) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId().equals(dialogId)) {
                log.error("You are deleting user from the dialog");
                DialogDto dialogDtoWithTime = dialogFacade.deleteUserFromDialog(dialogId, userId);
                return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
            }

        }
        log.error("You are trying to delete user from the someone else dialog");
        throw new RestError("You are trying to delete user from the someone else dialog");

    }

}