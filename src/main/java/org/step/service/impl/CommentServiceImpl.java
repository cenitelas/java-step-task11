package org.step.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.step.configuration.exception.BadMessageException;
import org.step.configuration.exception.NotFoundException;
import org.step.model.Comment;
import org.step.repository.CommentRepository;
import org.step.service.CommentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService<Comment> {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        isBadMessage(comment);
        return commentRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByMessageId(Long id) {
        return commentRepository.findAllByMessage_Id(id);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with ID %d not found", id)));
    }

    private void isBadMessage(Comment comment) {
        String[] badWords = {"Лох", "Bitch", "Гад", "Asshole"};

        Arrays.stream(badWords)
                .filter(word -> comment.getDescription().contains(word))
                .findAny()
                .ifPresent(word -> {
                    throw new BadMessageException(
                            String.format("This is bad word!!! Be careful %s", comment.getUser().getUsername())
                    );
                });
    }
}
