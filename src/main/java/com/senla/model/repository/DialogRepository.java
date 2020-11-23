package com.senla.model.repository;

import com.senla.model.entity.Dialog;
import com.senla.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<Dialog,Long> {
    Dialog getDialogByName(String name);
}
