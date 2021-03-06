package com.senla.repository;

import com.senla.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getMessagesByDialog_Id(Long dialogId);
    List<Message> getMessageByUser_Id(Long userId);
}
