/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storytel.board.service.impl;

import com.storytel.board.entities.Message;
import com.storytel.board.repository.MessageRepository;
import com.storytel.board.service.MessageService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author augus
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message create(Message message) {
        validateMessage(message);
        message.setDateCreated(LocalDateTime.now());
        Message newMessage = messageRepository.save(message);
        newMessage.setOwner(true);
        return newMessage;
    }

    private void validateMessage(Message message) throws IllegalArgumentException {
        if (message.getUsername() == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if(message.getText() == null || message.getText().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null");
        }
    }

    @Override
    public Message edit(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException {
        validateMessage(message);
        Message ownMessage = getUserMessage(message);
        ownMessage.setText(message.getText());
        ownMessage.setEdited(true);
        ownMessage = messageRepository.save(ownMessage);
        ownMessage.setOwner(true);
        return ownMessage;
    }

    @Override
    public void delete(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException {
        Message ownMessage = getUserMessage(message);
        messageRepository.delete(ownMessage);
    }

    @Override
    public List<Message> getAll(String username) {
        
        List<Message> messages = messageRepository.findAll();
        
        for (Message message : messages) {
            if (message.getUsername().equals(username)){
                message.setOwner(true);
            }
        }
        return messages;
    }

    @Override
    public Message getUserMessage(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException, IllegalArgumentException {
        if (message.getId() == null) {
            throw new IllegalArgumentException("The id must not be null");
        }
        Message ownMessage = messageRepository.findById(message.getId()).get();
        if (ownMessage.getUsername().equals(message.getUsername())) {
            return ownMessage;
        } else {
            throw new PermissionDeniedDataAccessException("This message does not belong to you", new Throwable());
        }
    }

}
