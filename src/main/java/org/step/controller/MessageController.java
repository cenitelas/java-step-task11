package org.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.step.model.Message;
import org.step.model.User;
import org.step.model.UserDetailsImpl;
import org.step.service.MessageService;

@Controller
public class MessageController {

    private final MessageService<Message> messageService;

    @Autowired
    public MessageController(MessageService<Message> messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/cabinet/message/save")
    public String saveMessage(
            @RequestParam(name = "description") String description,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Message message = new Message();
        message.setDescription(description);
        message.setUser(new User(userDetails.getUserDetailsId(), userDetails.getUsername()));

        messageService.save(message);

        return "cabinet";
    }
}
