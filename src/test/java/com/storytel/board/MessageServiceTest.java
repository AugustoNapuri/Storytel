package com.storytel.board;

import com.storytel.board.entities.Message;
import com.storytel.board.repository.MessageRepository;
import com.storytel.board.service.MessageService;
import com.storytel.board.service.impl.MessageServiceImpl;
import com.sun.media.sound.InvalidDataException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import org.springframework.dao.PermissionDeniedDataAccessException;

public class MessageServiceTest extends StorytelApplicationTests {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void addMessage_withUsername_ok() {
        String username = "Augusto";
        String text = "Hi everyone!";
        Message message = MessageBuilder.normal()
                .withUsername(username)
                .withText(text)
                .build();
        Message newMessage = messageService.create(message);

        assertEquals(newMessage.getText(), text);
        assertEquals(newMessage.getUsername(), username);
        assertNotNull(newMessage.getId());
        assertNotNull(newMessage.getDateCreated());
        System.out.println("#date" + newMessage.getDateCreated());
        assertTrue(newMessage.isOwner());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessage_withUsernameNull_throwException() {
        Message message = MessageBuilder.normal()
                .withUsername(null)
                .build();

        messageService.create(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMessage_withTextNull_throwException() {
        Message message = MessageBuilder.normal()
                .withText(null)
                .build();

        messageService.create(message);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addMessage_withTextEmpty_throwException() {
        Message message = MessageBuilder.normal()
                .withText("")
                .build();

        messageService.create(message);
    }

    @Test
    public void getAll_descOrderAndOwner() {
        String username = "Owner";
        String text = "Hi everyone!";
        Message message = MessageBuilder.normal()
                .withUsername(username)
                .withText(text)
                .build();
        messageService.create(message);

        List<Message> messages = messageService.getAll(username);

        assertEquals(messages.size(), messageRepository.count());
        assertTrue(messages.get(0).getDateCreated().isBefore(messages.get((int) messageRepository.count() - 1).getDateCreated()));
        assertTrue(messages.get(messages.size() - 1).isOwner());
    }

    @Test
    public void getAll_notOwner_allFalse() {
        String username = "notOwner";
        List<Message> messages = messageService.getAll(username);
        for (Message message : messages) {
            assertFalse(message.isOwner());
        }
    }

    @Test
    public void getAll_nullUsername_allFalse() {
        String username = null;
        List<Message> messages = messageService.getAll(username);
        for (Message message : messages) {
            assertFalse(message.isOwner());
        }
    }

    @Test
    public void deleteMessage_withIdExistence_ok() {
        Long id = 1L;
        String username = "Augusto";
        Message message = MessageBuilder.normal()
                .withId(id)
                .withUsername(username)
                .build();
        assertTrue(messageRepository.existsById(id));
        messageService.delete(message);

        assertFalse(messageRepository.existsById(id));
    }

    @Test(expected = PermissionDeniedDataAccessException.class)
    public void getUserMessage_foreignUser_throwException() {
        Long id = 1L;
        String username = "Octavio";
        Message message = MessageBuilder.normal()
                .withId(id)
                .withUsername(username)
                .build();
        assertTrue(messageRepository.existsById(id));
        messageService.getUserMessage(message);
    }

    @Test(expected = NoSuchElementException.class)
    public void getUserMessage_inexistenceMsg_throwException() {
        Long id = 21L;
        String username = "Augusto";
        Message message = MessageBuilder.normal()
                .withId(id)
                .withUsername(username)
                .build();
        assertFalse(messageRepository.existsById(id));
        messageService.getUserMessage(message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserMessage_withoutId_throwException() {
        Message message = MessageBuilder.normal().build();
        messageService.getUserMessage(message);
    }

    @Test
    public void editMessage_withOwner_ok() {
        Long id = 1L;
        String username = "Augusto";
        String text = "Bye Everyone";
        Message message = MessageBuilder.normal()
                .withId(id)
                .withUsername(username)
                .withText(text)
                .build();
        assertFalse(messageRepository.findById(id).get().getText().equals(text));
        Message messageEdited = messageService.edit(message);
        assertTrue(messageRepository.findById(id).get().getText().equals(text));
        assertTrue(messageEdited.getText().equals(text));
        assertTrue(messageEdited.isEdited());
        assertTrue(messageEdited.isOwner());
    }

}
