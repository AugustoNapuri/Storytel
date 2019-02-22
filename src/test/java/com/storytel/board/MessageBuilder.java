/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storytel.board;

import com.storytel.board.entities.Message;
import java.time.LocalDateTime;

/**
 *
 * @author augus
 */
public class MessageBuilder {
    
    private Long id;
    private String username;
    private String text;
    private LocalDateTime dateCreated;
    private boolean edited;
    
    public Message build(){ 
        Message message = new Message(username, text);
        message.setId(id);
        message.setDateCreated(dateCreated);
        message.setEdited(edited);
        return message;
    }
    
    public static MessageBuilder normal() {
        return new MessageBuilder()
                .withId(null)
                .withUsername("Augusto")
                .withText("normal text")
                .withDateCreated(null)
                .withEdited(false);
    }
    
    public MessageBuilder withUsername(String username) {
        this.username = username;
        return this;
    }
    
    public MessageBuilder withText(String text) { 
        this.text = text;
        return this;
    }
    
    public MessageBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    
    public MessageBuilder withDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }
    
    public MessageBuilder withEdited(boolean edited) {
        this.edited = edited;
        return this;
    }
}
