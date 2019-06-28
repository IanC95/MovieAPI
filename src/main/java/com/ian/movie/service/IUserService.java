package com.ian.movie.service;

import com.ian.movie.exception.UserNotValidatedException;

public interface IUserService {
    boolean userAuthenticated(String userName, String authCode)throws UserNotValidatedException;
}
