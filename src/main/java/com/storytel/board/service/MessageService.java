/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storytel.board.service;

import com.storytel.board.entities.Message;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.dao.PermissionDeniedDataAccessException;

/**
 *
 * @author augus
 */
public interface MessageService {
    
    public Message create(Message message);
    public Message edit(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException;
    public void delete(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException;
    public List<Message> getAll(String username);
    public Message getUserMessage(Message message) throws NoSuchElementException, PermissionDeniedDataAccessException, IllegalArgumentException;
}
