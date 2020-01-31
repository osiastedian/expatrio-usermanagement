package com.expatrio.usermanager.user.services;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.domain.UserRole;
import com.expatrio.usermanager.user.dto.UserDto;
import com.expatrio.usermanager.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public UserDto getUser(UUID userId) {
        return new UserDto(userRepository.getById(userId));
    }

    public UserDto getUserByUserNameAndPassword(String userName, String password) {
        PasswordEncoder encoder = this.passwordEncoder();
        String encryptedPassword = encoder.encode(password);
        return new UserDto(userRepository.getUserByUsernameAndPassword(userName, encryptedPassword));
    }

    public UserDto createUser(User user) throws Exception {
        User userExists = userRepository.getUserByUsername(user.getUsername());
        if(userExists != null) {
            throw new Exception("User already exists");
        }
        user.setPassword(this.passwordEncoder().encode(user.getPassword()));
        User createdUser = userRepository.save(user);
        return new UserDto(createdUser);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
//        if(userName.equals("default")) {
//            User user = User.builder()
//                    .role(UserRole.ADMIN)
//                    .id(UUID.fromString("8ac0f744-8493-4ca6-b6e8-a52afcda63b7"))
//                    .firstName("Default")
//                    .lastName("Default")
//                    .password(passwordEncoder().encode("default"))
//                    .username("default")
//                    .build();
//            return user;
//        }
        User user = userRepository.getUserByUsername(userName);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("%s does not exists", userName));
        }
        return user;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        Page<UserDto> pageDto = page.map(UserDto::new);
        return pageDto;
    }

    @Override
    public UserDto updateUser(UUID userId, UserDto user) throws IllegalArgumentException {
        if(userId == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }
        User userFound = this.userRepository.getById(user.getId());
        userFound.setFirstName(user.getFirstName());
        userFound.setLastName(user.getLastName());
        userFound.setRole(user.getRole());
        return new UserDto(userRepository.save(userFound));
    }

    @Override
    public boolean changePassword(UUID userId, String oldPassword, String newPassword) {
        if(userId == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }
        User userFound = this.userRepository.getById(userId);
        if(userFound == null) {
            throw  new IllegalArgumentException("User not Found");
        }
        String oldEncrypted = passwordEncoder().encode(oldPassword);
        if(!oldEncrypted.equals(userFound.getPassword())) {
            throw new IllegalArgumentException("Wrong Password");
        }
        userFound.setPassword(this.passwordEncoder().encode(newPassword));
        return userRepository.save(userFound) != null;
    }

    @Override
    public UserDto deleteUser(UUID userId) {
        if(userId == null) {
            throw new IllegalArgumentException("Invalid User ID");
        }
        User userFound = this.userRepository.getById(userId);
        if(userFound == null) {
            throw  new IllegalArgumentException("User not Found");
        }
        userRepository.delete(userFound);
        return new UserDto(userFound);
    }
}
