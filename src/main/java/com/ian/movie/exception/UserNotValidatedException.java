package com.ian.movie.exception;

import lombok.Getter;

@Getter
public class UserNotValidatedException extends Exception{
    public UserNotValidatedException(String userId) {
        super(String.format("User '%s' not validated", userId));
    }
}
