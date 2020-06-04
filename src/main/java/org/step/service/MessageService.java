package org.step.service;

import org.step.model.Message;

import java.util.List;

public interface MessageService<T extends Message> {

    T save(T t);

    boolean delete(T t);

    List<T> findAll();

    T findById(Long id);
}
