package org.step.service;

import org.step.model.Comment;

import java.util.List;

public interface CommentService<T extends Comment> {

    T save(T t);

    void delete(T t);

    T update(T t);

    List<T> findAllByMessageId(Long id);

    T findById(Long id);
}
