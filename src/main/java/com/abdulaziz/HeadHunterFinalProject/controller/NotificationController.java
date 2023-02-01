package com.abdulaziz.HeadHunterFinalProject.controller;


import com.abdulaziz.HeadHunterFinalProject.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping("/send/message")
    public void sendMessage(@RequestParam("email") String email,@RequestParam("message") String message){
        notificationService.sendMessage(email,message);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping("/send/code")
    public void sendCode(@RequestParam("email") String email){
        notificationService.sendCode(email);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping("/code/verify")
    public Boolean verifyCode(@RequestParam("email") String email, @RequestParam("code") String code){
        return notificationService.verify(email,code);
    }
}
