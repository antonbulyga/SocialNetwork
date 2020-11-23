package com.senla.model.service;

import com.senla.model.entity.Dialog;
import com.senla.model.entity.User;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

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
