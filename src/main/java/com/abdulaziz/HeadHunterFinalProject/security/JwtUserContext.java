package com.abdulaziz.HeadHunterFinalProject.security;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtUserContext {

    private Long id;
    private String email;

}
