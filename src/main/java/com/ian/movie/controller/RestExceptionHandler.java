package com.ian.movie.controller;

import com.ian.movie.exception.CommentValidationException;
import com.ian.movie.exception.MovieValidationException;
import com.ian.movie.exception.NoCommentsException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.exception.PageValidationException;
import com.ian.movie.exception.UserNotValidatedException;
import com.ian.movie.model.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoMovieException.class)
    public ApiError handleNoMovieOfId(NoMovieException e){
        return new ApiError(String.format("No movie of id, %s", e.getMessage()));
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoCommentsException.class)
    public ApiError handleNoCommentOfId(NoCommentsException e){
        if(e.getCommentId() != null) {
            return new ApiError(String.format("No comment of id: %s for movie: %s", e.getCommentId(), e.getMovieId()));
        }else{
            return new ApiError(String.format("No comments found for movie: %s", e.getMovieId()));
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MovieValidationException.class)
    public ApiError handleInvalidMoviePost(MovieValidationException e){
        return new ApiError(e.getErrors());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentValidationException.class)
    public ApiError handleInvalidCommentPost(CommentValidationException e){
        return new ApiError((e.getErrors()));
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageValidationException.class)
    public ApiError handleInvalidCommentPost(PageValidationException e){
        return new ApiError((e.getErrors()));
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(UserNotValidatedException.class)
    public ApiError handleUnvalidatedUser(UserNotValidatedException e){
        return new ApiError(e.getMessage());
    }
}
