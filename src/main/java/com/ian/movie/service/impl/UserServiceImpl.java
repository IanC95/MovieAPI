package com.ian.movie.service.impl;

import com.ian.movie.exception.UserNotValidatedException;
import com.ian.movie.model.dao.UserDAO;
import com.ian.movie.repository.UserRepository;
import com.ian.movie.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean userAuthenticated(String userName, String authCode) throws UserNotValidatedException{
        Optional<UserDAO> optionalUserDAO = this.userRepository.findById(userName);

        UserDAO userDAO = optionalUserDAO.orElseThrow(() -> new UserNotValidatedException(userName));

        if(authCode.hashCode() == userDAO.getAuthHash()){
            return true;
        }

        throw new UserNotValidatedException(userName);
    }
}
