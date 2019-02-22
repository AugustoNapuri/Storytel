package com.storytel.board.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import org.springframework.transaction.annotation.Transactional;

@Entity
public class Message implements Serializable {

    public Message() {
    }

    private Long id;
    private String text;
    private String username;
    private LocalDateTime dateCreated;
    private boolean edited;
    private boolean owner;

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
        this.dateCreated = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    @Transient
    public boolean isOwner() {
        return owner;
    }

}
