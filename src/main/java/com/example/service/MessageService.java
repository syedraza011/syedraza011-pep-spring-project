package com.example.service;
import java.util.*;
import com.example.entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import com.example.repository.MessageRepository;
import com.example.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
   
    public MessageService(MessageRepository messageRepository){
        this.messageRepository= messageRepository;
    }

    public Message createNewMessage(Message message) {

        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Invalid message text!");
        }
        // Message newMessage = new Message();
        // newMessage.setPostedBy(postedById);
        // newMessage.setMessageText(messageText);
        // newMessage.setTimePostedEpoch(System.currentTimeMillis());

        return messageRepository.save(message);
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
