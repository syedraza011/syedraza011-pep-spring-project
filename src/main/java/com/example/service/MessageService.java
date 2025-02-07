// package com.example.service;

// import org.springframework.stereotype.Service;

// @Service
// public class MessageService {
// }
package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import com.example.entity.Message;
import com.example.exception.UnauthorizedException;
import com.example.repository.MessageRepository;


@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
   
    public MessageService(MessageRepository messageRepository){
        this.messageRepository= messageRepository;
    }

    public Message createNewMessage(Integer postedById, String messageText) {
        if (messageText == null || messageText.isEmpty()) {
            throw new IllegalArgumentException("Message text cannot be empty.");
        }
        Message newMessage = new Message();
        newMessage.setPostedBy(postedById);
        newMessage.setMessageText(messageText);
        newMessage.setTimePostedEpoch(System.currentTimeMillis());

    return messageRepository.save(newMessage);
}

        
    
    public List<Message> GetAllMessages(){
        List<Message> messages = messageRepository.findAll();
        if (messages.isEmpty()) {
            throw new EntityNotFoundException("No messages found in the database!!");
        }
        return messages;
    }


    public Message GetMessageById(int messageId){
       Message message= messageRepository.getById(messageId);
       return message;
    }
    public ResponseEntity<String> DeleteMessagebyId(int idToBeDeleted){
    
        if (!messageRepository.existsById(idToBeDeleted)) {
            throw new EntityNotFoundException("Message with ID " + idToBeDeleted + " not found.");
        }

        messageRepository.deleteById(idToBeDeleted);
        return ResponseEntity.ok("Message deleted successfully.");
    }


   
    public Message UpdateMessagebyId(Integer messageId, Integer accountId, Message updatedMessage){
        messageRepository.getById(messageId);
        return null;
    }
   
    public  Optional<Message> getAllMessagesByUserId(int userId) {
        Optional<Message> messages = messageRepository.findById(userId);
        
        if (messages.isEmpty()) {
            throw new EntityNotFoundException("No messages found for user ID: " + userId);
        }
    
        return messages;
    }
    
   
    public Message updateMessageById(Integer messageId, Integer accountId, Message updatedMessage) {
        Message currMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("Message not found"));
        if (!currMessage.getPostedBy().equals(accountId)) {
            throw new UnauthorizedException("You do not have permission to update this message.");
        }
        if (updatedMessage.getMessageText() != null) {
            currMessage.setMessageText(updatedMessage.getMessageText());
        }
        if (updatedMessage.getPostedBy()!= null) {
            currMessage.setPostedBy(accountId);
        }
        if (updatedMessage.getTimePostedEpoch()!= null) {
            currMessage.setTimePostedEpoch(updatedMessage.getTimePostedEpoch());
        }
    
        return messageRepository.save(currMessage);
    }
  
    
}
