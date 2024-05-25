package org.yosua.binfood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yosua.binfood.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ValidationService validationService;

    @Autowired
    public UserService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }
}
