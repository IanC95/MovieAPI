package com.ian.movie.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentValidationException extends Exception {
    private final List<String> errors;

    public CommentValidationException(List<String> errors) {
        this.errors = errors;
    }
}
