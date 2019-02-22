/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storytel.board.controller.rest;

import com.storytel.board.entities.Message;
import com.storytel.board.service.MessageService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @GetMapping()
    public List<Message> getAll(String username) {
        return messageService.getAll(username);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Message create(@RequestBody Message message) {
        return messageService.create(message);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Message edit(@RequestBody Message message) {
        return messageService.edit(message);
    }
}
