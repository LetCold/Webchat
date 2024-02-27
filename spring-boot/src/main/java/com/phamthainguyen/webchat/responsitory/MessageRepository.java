package com.phamthainguyen.webchat.responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phamthainguyen.webchat.models.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.sender = ?1 AND m.receiver = ?2 ORDER BY m.timestamp DESC")
    List<Message> findTop10MessagesBySenderAndReceiver(Long sender, Long receiver);

    @Query("SELECT m FROM Message m WHERE m.sender = ?1 AND m.receiver = ?2 ORDER BY m.timestamp DESC")
    List<Message> findTop10MessagesByReceiverAndSender(Long receiver, Long sender);
}
