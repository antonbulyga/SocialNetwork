package com.senla.repository;

import com.senla.entity.Dialog;
import com.senla.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Dialog getDialogByName(String name);
}
