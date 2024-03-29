package com.moditech.ecommerce.service;

import com.moditech.ecommerce.model.User;
import com.moditech.ecommerce.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        User userByEmail = userRepository.findByEmail(user.getEmail());

        if (userByEmail != null) {
            log.warn("Email is already existing");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setUserRole(user.getUserRole());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User loginUser(String email, String password) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid password");
        }
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getUserByRole(String userRole) {
        return userRepository.findByUserRole(userRole);
    }

    public void updateUserRole(String email, String userRole) {
        System.out.println("userRole: " + userRole);
        System.out.println("email: " + email);
        User user = userRepository.findByEmail(email);
        assert user != null;
        System.out.println("user Details: " + user);
        user.setUserRole(userRole);
        userRepository.save(user);
    }

    public void updateRoleStaffIsEnable(String email, Boolean isEnable) {
        User user = userRepository.findByEmail(email);
        assert user != null;
        user.setIsEnable(isEnable);
        userRepository.save(user);
    }

    public List<User> getListUser() {
        return userRepository.findAll();
    }

    public void updatePassword(String email, User user) {
        User setUser = userRepository.findByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encodedPassword = encoder.encode(user.getPassword());
        setUser.setPassword(encodedPassword);
        userRepository.save(setUser);
    }

    public User updateIsEnableUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setIsEnable(true);
            userRepository.save(user);
        }
        return user;
    }
}
