package com.ian.movie.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class MovieValidationException extends Exception {

    private final List<String> errors;

    public MovieValidationException(List<String> errors) {
        this.errors = errors;
    }

}
