package com.phamthainguyen.webchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phamthainguyen.webchat.models.response.MessageResponse;
import com.phamthainguyen.webchat.service.MessageService;

@RestController
@RequestMapping("/api/user/message")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{id}")
    public MessageResponse getMessage(@PathVariable Long id) {
        return messageService.getMessage(id);
    }
}
