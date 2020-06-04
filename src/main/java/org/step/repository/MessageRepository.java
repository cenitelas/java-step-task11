package org.step.repository;

import org.step.model.Message;

import java.util.List;

public interface MessageRepository<T extends Message> {

    T save(T t);

    boolean delete(T t);

    List<T> findAll();
}
