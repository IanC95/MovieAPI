package com.ian.movie.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class PageValidationException extends Exception{
    private final List<String> errors;

    public PageValidationException(List<String> errors) {
        this.errors = errors;
    }
}
