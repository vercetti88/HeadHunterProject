package com.abdulaziz.HeadHunterFinalProject.service;


import com.abdulaziz.HeadHunterFinalProject.dto.JwtTokenDto;
import com.abdulaziz.HeadHunterFinalProject.dto.LoginDto;
import com.abdulaziz.HeadHunterFinalProject.dto.UserDTO;
import com.abdulaziz.HeadHunterFinalProject.dto.UserSearchDTO;
import com.abdulaziz.HeadHunterFinalProject.model.RoleEntity;
import com.abdulaziz.HeadHunterFinalProject.model.RoleType;
import com.abdulaziz.HeadHunterFinalProject.model.UserEntity;
import com.abdulaziz.HeadHunterFinalProject.repository.UserRepository;
import com.abdulaziz.HeadHunterFinalProject.security.JwtUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;




    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getById(Long id){
        return Optional.ofNullable(userRepository.findById(id).orElse(null));
    }

    @Transactional
    public void save(UserEntity userEntity){
        userRepository.save(userEntity);
    }
    @Transactional
    public void update(long id, UserEntity userEntity){
        userEntity.setId(id);
        userRepository.save(userEntity);
    }
    @Transactional
    public void delete(long id){
        userRepository.deleteById(id);
    }

    @Lazy
    @Autowired
    public UserService(UserRepository userRepository, ModelMapper mapper, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.encoder = encoder;

    }

    public JwtTokenDto login(LoginDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        String jwt = jwtUtils.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return JwtTokenDto.builder()
                .token(jwt)
                .build();
    }

    @Transactional
    public void registration(UserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        UserEntity user = mapper.map(dto, UserEntity.class);
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(new RoleEntity(RoleType.ROLE_CLIENT));
        userRepository.save(user);
    }

    public Page<UserDTO> getByParams(Pageable pagerequest, UserSearchDTO searchDTO){
        Page<UserEntity> list = userRepository.findByParams(searchDTO.getSearchField(),searchDTO.getIsActive(),pagerequest);
        return list.map(v->mapper.map(v,UserDTO.class));
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(username);

        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(userEntity.get().getId());
        userDto.setEmail(userEntity.get().getEmail());
        userDto.setPassword(userEntity.get().getPassword());
        userDto.setRoles(Collections.singleton(userEntity.get().getRole()));
        userDto.setIsActive(userEntity.get().isActive());
        return userDto;
    }


}
