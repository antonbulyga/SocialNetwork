package com.senla.controller;

import com.senla.dto.dialog.DialogDto;
import com.senla.dto.user.UserNestedDto;
import com.senla.entity.Dialog;
import com.senla.entity.User;
import com.senla.exception.RestError;
import com.senla.facade.DialogFacade;
import com.senla.facade.UserFacade;
import com.senla.service.locale.MessageByLocaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * * @author  Anton Bulyha
 * * @version 1.0
 * * @since   2020-08-12
 */
@RestController
@RequestMapping(value = "/dialogs")
@Slf4j
public class DialogController {

    private final DialogFacade dialogFacade;
    private final UserFacade userFacade;
    private final MessageByLocaleService messageByLocaleService;

    @Autowired
    public DialogController(DialogFacade dialogFacade, UserFacade userFacade, MessageByLocaleService messageByLocaleService) {
        this.dialogFacade = dialogFacade;
        this.userFacade = userFacade;
        this.messageByLocaleService = messageByLocaleService;
    }

    /**
     * Get all dialogs of the user
     *
     * @return list of dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "")
    public ResponseEntity<List<DialogDto>> getAllUsersDialogs() {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogList = user.getDialogs();
        log.info("Getting user dialogs");
        List<DialogDto> dialogDtoList = dialogFacade.convertDialogListToLikeDto(dialogList);
        return new ResponseEntity<>(dialogDtoList, HttpStatus.OK);
    }

    /**
     * Add dialog
     *
     * @param dialogDto dialog dto
     * @return dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/add")
    public ResponseEntity<DialogDto> addDialog(@Valid @RequestBody DialogDto dialogDto) {
        User user = userFacade.getUserFromSecurityContext();
        List<UserNestedDto> userFromDtoList = dialogDto.getUserList();
        for (UserNestedDto d : userFromDtoList) {
            if (user.getId().equals(d.getId())) {
                DialogDto dialogDtoWithData = dialogFacade.addDialog(dialogDto);
                log.info("Adding a new dialog");
                return new ResponseEntity<>(dialogDtoWithData, HttpStatus.OK);
            }

        }
        log.error("You are trying to create dialog without your user inside");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.add"));
    }

    /**
     * Delete dialog
     *
     * @param id dialog id
     * @return string response
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteDialog(@RequestParam(name = "id") Long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId().equals(id)) {
                dialogFacade.deleteDialog(id);
                log.info("Deleting the dialog");
                return ResponseEntity.ok()
                        .body(messageByLocaleService.getMessage("dialog.success.delete"));
            }

        }
        log.error("You are trying to delete dialog where you do not participate");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.delete"));

    }

    /**
     * Get dialog by id
     *
     * @param id dialog id
     * @return dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    public ResponseEntity<DialogDto> getDialogById(@PathVariable(name = "id") Long id) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId().equals(id)) {
                DialogDto dialogDto = dialogFacade.getDialogDto(id);
                return new ResponseEntity<>(dialogDto, HttpStatus.OK);
            }

        }
        log.error("You are trying to get dialog where you do not participate");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.get"));
    }

    /**
     * Update dialog
     *
     * @param dialogDto dialog dto
     * @return dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping(value = "/update")
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
        log.error("You are trying to update dialog where you do not participate");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.update"));
    }

    /**
     * Get dialog by name
     *
     * @param name dialog name
     * @return dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/search/name")
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
        log.error("You are trying to get dialog where you do not participate");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.get"));

    }

    /**
     * Add user to the dialog
     *
     * @param dialogId dialog id
     * @param userId   user id
     * @return dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping(value = "/add/user")
    public ResponseEntity<DialogDto> addUserToDialog(@RequestParam(name = "dialogId") Long
                                                             dialogId, @RequestParam(name = "userId") Long userId) {
        User userFromSecurityContext = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogsFromCurrentUser = userFromSecurityContext.getDialogs();
        for (Dialog d : dialogsFromCurrentUser) {
            int count = dialogFacade.userParticipateInDialogCheck(dialogId, userId);
            if (count == 0) {
                log.error("You are adding user to the dialog");
                DialogDto dialogDtoWithTime = dialogFacade.addUserToDialog(dialogId, userId);
                return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
            } else {
                log.error("The user is already in this dialogue");
                throw new RestError(messageByLocaleService.getMessage("dialog.invalid.user.add.exist"));
            }


        }
        log.error("You are trying to add user to the dialog where you do not participate");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.user.add"));

    }

    /**
     * Delete user from the dialog
     *
     * @param dialogId dialog id
     * @param userId   user id
     * @return dialog dto
     */
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping(value = "/delete/user")
    public ResponseEntity<DialogDto> deleteUserFromDialog(@RequestParam(name = "dialogId") Long
                                                                  dialogId, @RequestParam(name = "userId") Long userId) {
        User user = userFacade.getUserFromSecurityContext();
        List<Dialog> dialogs = user.getDialogs();
        for (Dialog d : dialogs) {
            if (d.getId().equals(dialogId)) {
                log.error("You are deleting user from the dialog");
                DialogDto dialogDtoWithTime = dialogFacade.deleteUserFromDialog(dialogId, userId);
                return new ResponseEntity<>(dialogDtoWithTime, HttpStatus.OK);
            }

        }
        log.error("You are trying to delete user from dialog where you do not participate");
        throw new RestError(messageByLocaleService.getMessage("dialog.invalid.user.delete"));

    }

}