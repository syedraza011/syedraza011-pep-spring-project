package com.example.service;
import java.util.*;
import com.example.entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;

import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
   
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository= messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createNewMessage(Message message) {

        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Invalid message text!");
        }
       if( accountRepository.findById(message.getPostedBy()) != null){
        return messageRepository.save(message);
       }
        return null;
    }
   
    public List<Message> GetAllMessages(){
        List<Message> messages = messageRepository.findAll();
        if (messages.isEmpty()) {
            throw new EntityNotFoundException("No messages found in the database!!");
        }
        return messages;
    }
    
    public Message getMessageById(int messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with ID: " + messageId));
    }
    
    
    public ResponseEntity<Integer> DeleteMessagebyId(Integer messageId){
        if (!messageRepository.existsById(messageId)) {
            return ResponseEntity.ok().build();
        }
        messageRepository.deleteById(messageId);
        return ResponseEntity.ok(1);
    }
    
    public  List<Message> getMessagesByUserId(Integer userId) {

        List<Message> messages = messageRepository.findByPostedBy(userId);
        // if (messages.isEmpty()) {
        //     throw new EntityNotFoundException("No messages found for user ID: " + userId);
        // }
        return messages;
    }
    
    public boolean updateMessageById(Integer messageId, String messageText) {
        Optional<Message> currMessage = messageRepository.findById(messageId);
                
      if(currMessage.isPresent()&&  messageText.length() <= 255 ){
            Message message=currMessage.get();
            messageRepository.save(message);
            return true;
        }
      return false;
    }
}
