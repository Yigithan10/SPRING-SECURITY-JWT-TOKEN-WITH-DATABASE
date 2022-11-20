package com.example.jwtTokenWithDatabase.controllers;

import com.example.jwtTokenWithDatabase.services.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String messageJwt(){
        return messageService.messageJwt();
    }
}
