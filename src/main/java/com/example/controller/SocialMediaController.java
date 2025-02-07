package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping
@Component
public class SocialMediaController {
    @Autowired 
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @DeleteMapping("/messages/{messageId}")
    public  ResponseEntity <String> DeleteMessageById(@PathVariable int messageId ){
  
        return messageService.DeleteMessagebyId(messageId);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Integer userId, String message) {
        try {
            Message savedMessage = messageService.createNewMessage( userId, message);
            return ResponseEntity.status(200).body(savedMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(409).body(null);
        }
    }
    

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Message> updateMessageById(
                                @PathVariable Integer messageId, 
                                @RequestParam Integer accountId,
                             @RequestBody Message updatedMessage) {
        try {
            Message message = messageService.updateMessageById(messageId, accountId, updatedMessage);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(409).body(null);
        }
    }
        @GetMapping("/messages/{messageId}")
    public  ResponseEntity <Message> GetMessageById(int messageId ){
        try{
            Message message = messageService.GetMessageById(messageId);
            return ResponseEntity.ok(message);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }
    @GetMapping("/messages")
    public  ResponseEntity <List<Message>> getAllMessages( ){
        try{
            List<Message> messages = messageService.GetAllMessages();
            return ResponseEntity.ok(messages);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }
    @GetMapping("/accounts/{accountId}/messages")
    public  ResponseEntity <Optional<Message>> getAllMessagesByUserId( Integer accountId){
        try{
            Optional<Message> messages = messageService.getAllMessagesByUserId(accountId);
            return ResponseEntity.ok(messages);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }





    @PostMapping("/register")
    public ResponseEntity <Account> userRegistration(@RequestBody Account account){
         try{
             Account createdAccount= accountService.userRegistration(account);
             if(createdAccount!=null){
                 return ResponseEntity.ok(createdAccount);
             }
             return ResponseEntity.status(409).build();
         }catch(Exception e){
             e.printStackTrace();
         }
         return null;
     }
    @PostMapping("/login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        try {
           
            Account loggedInAccount = accountService.loginUser(account.getUsername(), account.getPassword());
            
            if (loggedInAccount != null) {
                return ResponseEntity.ok(loggedInAccount);
            }
            
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(501).build();
        }
       
    }
}
