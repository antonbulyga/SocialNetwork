package com.senla.repository;

import com.senla.entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Dialog getDialogByName(String name);
}
