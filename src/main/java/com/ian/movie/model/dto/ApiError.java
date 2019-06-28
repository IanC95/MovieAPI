package com.ian.movie.model.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiError{
    private List<String> errors;

    public ApiError(String message) {
        this.errors = new ArrayList<>();
        this.errors.add(message);
    }

    public ApiError(List<String> errors){
        this.errors = errors;
    }
}
