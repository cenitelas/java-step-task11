package org.step.repository.impl;

import org.springframework.stereotype.Repository;
import org.step.model.Message;
import org.step.repository.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepository<Message> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Message save(Message message) {
        entityManager.persist(message);
        return message;
    }

    @Override
    public boolean delete(Message message) {
        int update = entityManager.createQuery("delete from Message m where m.id=:id")
                .setParameter("id", message.getId())
                .executeUpdate();
        return update != -1;
    }

    @Override
    public List<Message> findAll() {
        return entityManager.createQuery("select m from Message m", Message.class)
                .getResultList();
    }
}
