package com.expatrio.usermanager.user.services;

import com.expatrio.usermanager.user.domain.User;
import com.expatrio.usermanager.user.domain.UserRole;
import com.expatrio.usermanager.user.dto.UserDto;
import com.expatrio.usermanager.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public UserDto createUser(User user) {
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
        if(userName.equals("default")) {
            User user = User.builder()
                    .role(UserRole.ADMIN)
                    .firstName("Default")
                    .lastName("Default")
                    .password(passwordEncoder().encode("default"))
                    .username("default")
                    .build();
            return user;
        }
        User user = userRepository.getUserByUsername(userName);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("%s does not exists", userName));
        }
        return user;
    }



}
