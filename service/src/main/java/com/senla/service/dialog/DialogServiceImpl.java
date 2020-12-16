package com.senla.service.dialog;

import com.senla.entity.Dialog;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.DialogRepository;
import com.senla.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class DialogServiceImpl implements DialogService {

    private final DialogRepository dialogRepository;
    private final UserService userService;

    @Autowired
    public DialogServiceImpl(DialogRepository dialogRepository, UserService userService) {
        this.dialogRepository = dialogRepository;
        this.userService = userService;
    }


    public Dialog getDialog(Long id) {
        log.info("Getting dialog by id");
        return dialogRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Dialog with id = %s is not found", id)));
    }

    @Override
    public Dialog addDialog(Dialog dialog) {
        log.info("Adding dialog");
        dialogRepository.save(dialog);
        return dialog;
    }

    @Override
    public void deleteDialog(long id) {
        getDialog(id);
        log.info("Deleting dialog");
        dialogRepository.deleteById(id);
    }

    @Override
    public Dialog getDialogByName(String name) {
        log.info("Getting dialog by name");
        Dialog dialog = dialogRepository.getDialogByName(name);
        if (dialog == null) {
            throw new EntityNotFoundException("No dialog with this name");
        }
        return dialog;
    }


    @Override
    public Dialog updateDialog(Dialog dialog) {
        log.info("Updating dialog");
        dialogRepository.save(dialog);
        return dialog;
    }

    @Override
    public List<Dialog> getAllDialogs() {
        log.info("Getting all dialog");
        return dialogRepository.findAll();
    }

    @Override
    public Dialog addUserToDialog(Long dialogId, Long userId) {
        Dialog dialog = getDialog(dialogId);
        User user = userService.getUser(userId);
        List<User> users = dialog.getUserList();
        users.add(user);
        log.info("Adding user to the dialog");
        updateDialog(dialog);
        return dialog;
    }

    @Override
    public Dialog deleteUserFromDialog(Long dialogId, Long userId) {
        Dialog dialog = getDialog(dialogId);
        User user = userService.getUser(userId);
        List<User> users = dialog.getUserList();
        users.remove(user);
        log.info("Deleting user from the dialog");
        updateDialog(dialog);
        return dialog;
    }

}
