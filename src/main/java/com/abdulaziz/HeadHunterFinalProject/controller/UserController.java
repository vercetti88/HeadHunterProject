package com.abdulaziz.HeadHunterFinalProject.controller;


import com.abdulaziz.HeadHunterFinalProject.dto.JwtTokenDto;
import com.abdulaziz.HeadHunterFinalProject.dto.LoginDto;
import com.abdulaziz.HeadHunterFinalProject.dto.UserDTO;
import com.abdulaziz.HeadHunterFinalProject.model.UserEntity;
import com.abdulaziz.HeadHunterFinalProject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UserDTO> getUsers(){
        return userService.findAll().stream()
                .map(this::convertToUserDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/activate/{id}")
    public ResponseEntity<HttpStatus> activateUser(@PathVariable("id") long id){
        UserEntity user = userService.getById(id).get();
        if(!user.isActive())
            user.setActive(true);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/block/{id}")
    public ResponseEntity<HttpStatus> blockUser(@PathVariable("id") long id){
        UserEntity user = userService.getById(id).get();
        if(user.isActive())
            user.setActive(false);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping("/{id}")
    public Optional<UserDTO> getUserById(@PathVariable("id") long id){
        return Optional.ofNullable(convertToUserDTO(userService.getById(id).get()));
    }

    public UserEntity convertToUser(UserDTO userDTO){
        return mapper.map(userDTO, UserEntity.class);
    }
    public UserDTO convertToUserDTO(UserEntity userEntity){
        return mapper.map(userEntity, UserDTO.class);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody UserDTO userDTO){
        userService.save(convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody UserDTO userDTO, @PathVariable long id){
        userService.update(id,convertToUser(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") long id){
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @PostMapping("/registration")
    public void registration(@RequestBody UserDTO userDto) {
        userService.registration(userDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @GetMapping("/showUserInfo")
    public ResponseEntity<String> showUserInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        return ResponseEntity.ok(userDTO.getUsername());
    }
}
