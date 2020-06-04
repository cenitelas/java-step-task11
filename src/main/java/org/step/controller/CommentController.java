package org.step.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.step.model.Comment;
import org.step.model.Message;
import org.step.model.User;
import org.step.model.UserDetailsImpl;
import org.step.repository.MessageRepositorySpringData;
import org.step.service.CommentService;
import org.step.service.MessageService;
import org.step.service.UserService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService<Comment> commentService;
    private final MessageService<Message> messageService;
    private final UserService<User> userService;

    @GetMapping("/messages/{messageId}/comments")
    public String getComments(@PathVariable(name = "messageId") Long messageId, Model model) {
        List<Comment> allByMessageId = commentService.findAllByMessageId(messageId);

        model.addAttribute("comments", allByMessageId);

        return "comments";
    }

    @PostMapping("/messages/{messageId}/comments/save")
    public String saveComment(@PathVariable(name = "messageId") Long messageId,
                              @RequestParam(name = "description") @NotNull @NotBlank String description,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Message messageById = messageService.findById(messageId);
        User byUsername = userService.findByUsername(userDetails.getUsername());

        Comment newComment = Comment.builder()
                .description(description)
                .message(messageById)
                .user(byUsername)
                .build();

        Comment commentAfterSaving = commentService.save(newComment);

        return "index";
    }
}
