package org.step.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.configuration.exception.NotFoundException;
import org.step.model.Message;
import org.step.repository.MessageRepository;
import org.step.repository.MessageRepositorySpringData;
import org.step.service.MessageService;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService<Message> {

    private final MessageRepository<Message> messageRepository;
    private final MessageRepositorySpringData messageRepositorySpringData;

    @Autowired
    public MessageServiceImpl(MessageRepository<Message> messageRepository,
                              MessageRepositorySpringData messageRepositorySpringData) {
        this.messageRepository = messageRepository;
        this.messageRepositorySpringData = messageRepositorySpringData;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean delete(Message message) {
        return messageRepository.delete(message);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message findById(Long id) {
        return messageRepositorySpringData.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Message with ID %d not found", id)));
    }
}
