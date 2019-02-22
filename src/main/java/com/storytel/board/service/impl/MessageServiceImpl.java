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
        message.setDateCreated(LocalDateTime.now());
        return messageRepository.save(message);
    }

    @Override
    public Message edit(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException {
        Message ownMessage = getUserMessage(message);
        ownMessage.setText(message.getText());
        ownMessage.setEdited(true);
        return messageRepository.save(ownMessage);
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
