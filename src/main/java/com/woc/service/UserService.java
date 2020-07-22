package com.woc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woc.entity.User;
import com.woc.entity.UserCredentials;
import com.woc.repository.UserCredentialsRepository;
import com.woc.repository.UserRepository;

@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCredentialsRepository userCredRepository;

    public User createUser(User newUser) {
        return userRepository.createNewUser(newUser);
    }

    public void createUserCreds(UserCredentials userCreds) {
        System.out.println("setting User credentials");
        userCredRepository.addUserCredentails(userCreds);
    }

    UserService() {
        super();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCredentialsRepository getUserCredentialsRepository() {
        return userCredRepository;
    }

    public void setUserCredentialsRepository(UserCredentialsRepository userCredRepository) {
        this.userCredRepository = userCredRepository;
    }

}
