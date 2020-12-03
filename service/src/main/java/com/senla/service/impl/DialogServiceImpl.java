package com.senla.service.impl;


import com.senla.entity.Dialog;
import com.senla.entity.User;
import com.senla.exception.EntityNotFoundException;
import com.senla.repository.DialogRepository;
import com.senla.service.DialogService;
import com.senla.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
        return dialogRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Dialog with id = %s is not found", id)));
    }

    @Override
    public Dialog addDialog(Dialog dialog) {
        dialogRepository.save(dialog);
        return dialog;
    }

    @Override
    public void deleteDialog(long id) {
        getDialog(id);
        dialogRepository.deleteById(id);
    }

    @Override
    public Dialog getDialogByName(String name) {
        return dialogRepository.getDialogByName(name);
    }


    @Override
    public Dialog updateDialog(Dialog dialog) {
        dialogRepository.save(dialog);
        return dialog;
    }

    @Override
    public List<Dialog> getAllDialogs() {
        return dialogRepository.findAll();
    }

    @Override
    public Dialog addUserToDialog(Long dialogId, Long userId) {
        Dialog dialog = getDialog(dialogId);
        User user = userService.getUser(userId);
        List<User> users = dialog.getUserList();
        users.add(user);
        updateDialog(dialog);
        return dialog;
    }

    @Override
    public Dialog deleteUserFromDialog(Long dialogId, Long userId) {
        Dialog dialog = getDialog(dialogId);
        User user = userService.getUser(userId);
        List<User> users = dialog.getUserList();
        users.remove(user);
        updateDialog(dialog);
        return dialog;
    }

}
