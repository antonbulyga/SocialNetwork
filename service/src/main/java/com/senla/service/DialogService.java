package com.senla.service;

import com.senla.entity.Dialog;
import com.senla.entity.User;

import java.util.List;

public interface DialogService {
    Dialog addDialog(Dialog dialog);
    void delete(long id);
    Dialog getDialogByName(String name);
    Dialog editDialog(Dialog dialog);
    List<Dialog> getAll();
    Dialog addUserToDialog(Long dialogId,Long userId);
    Dialog getDialog(Long id);
    Dialog deleteUserFromDialog(Long dialogId,Long userId);
}
