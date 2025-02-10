package com.example.controller;
import java.util.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

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
    public  ResponseEntity <?> DeleteMessageById(@PathVariable int messageId ){
        return messageService.DeleteMessagebyId(messageId);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message) {
        try {
            Message savedMessage = messageService.createNewMessage(message);
            if(savedMessage==null){
                return ResponseEntity.status(400).build();
            }else {
                return ResponseEntity.status(200).body(savedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body(null);
        }
    }
    
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer>  updateMessageById(
                        @PathVariable Integer messageId,
                        @RequestBody Message message) {
       if (messageService.updateMessageById(messageId, message.getMessageText()) && !message.getMessageText().isEmpty()) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }
    
    /*
     ## 5: Our API should be able to retrieve a message by its ID.
        GET request on the endpoint GET localhost:8080/messages/{messageId}.
        The response body should contain a JSON representation of the message identified by the messageId.
        It is expected for the response body to simply be empty if there is no such message. 
        The response status should always be 200, which is the default.
     */
   
    @GetMapping("/messages/{messageId}")
    public  ResponseEntity <Message> GetMessageById(@PathVariable Integer messageId ){
        try{
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.ok(message);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(200).build();
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
    public  ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable Integer accountId){
        try{
           List<Message> messages = messageService.getMessagesByUserId(accountId);
            return ResponseEntity.ok(messages);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(200).build();
        }
    }
    @PostMapping("/register")
    public ResponseEntity <Account> userRegister(@RequestBody Account account){
         try{
             Account createdAccount= accountService.userRegister(account);
             if(createdAccount!=null){
                 return ResponseEntity.ok(createdAccount);
             }
             return ResponseEntity.status(200).build();
         }catch(Exception e){
              return ResponseEntity.status(409).build();
         }
        
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
            return ResponseEntity.status(401).build();
        }
       
    }
}
