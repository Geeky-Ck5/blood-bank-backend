package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientId(Long recipientId);
    List<Message> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
