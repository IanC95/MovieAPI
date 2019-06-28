package com.ian.movie.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NoCommentsException extends Exception {
    private String commentId;
    private String movieId;

    public NoCommentsException(String commentId, String movieId) {
        super();
        this.commentId = commentId;
        this.movieId = movieId;
    }

    public NoCommentsException(String movieId) {
        super();
        this.movieId = movieId;
    }
}
