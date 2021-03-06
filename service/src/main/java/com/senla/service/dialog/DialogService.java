package com.senla.service.dialog;

import com.senla.entity.Dialog;

import java.util.List;

public interface DialogService {
    Dialog addDialog(Dialog dialog);

    void deleteDialog(long id);

    Dialog getDialogByName(String name);

    Dialog updateDialog(Dialog dialog);

    List<Dialog> getAllDialogs();

    Dialog addUserToDialog(Long dialogId, Long userId);

    Dialog getDialog(Long id);

    Dialog deleteUserFromDialog(Long dialogId, Long userId);
}
