package com.abdulaziz.HeadHunterFinalProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private final RestTemplate restTemplate;
    @Autowired
    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessage(String email, String message){
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

        form.add("email", email);
        form.add("message", message);
        String url = "http://localhost:8085/email/send/message";
        restTemplate.postForEntity(url, form, String.class);
    }

    public void sendCode(String email){
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

        form.add("email", email);

        String url = "http://localhost:8085/email/send/code";

        restTemplate.postForEntity(url, form, String.class);
    }

    public Boolean verify(String email, String code){
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

        form.add("email", email);
        form.add("code", code);
        String url = "http://localhost:8085/email/send/verify?email="+email+"&code="+code;
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(url, Boolean.class);
        return responseEntity.getBody();
    }
}



