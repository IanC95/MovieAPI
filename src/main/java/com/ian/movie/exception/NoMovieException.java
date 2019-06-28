package com.ian.movie.exception;

public class NoMovieException extends Exception {
    private static final String NO_MOVIES_ERROR_MESSAGE = "No movies found";
    public NoMovieException(String message) {
        super(message);
    }

    public NoMovieException() {
        super(NO_MOVIES_ERROR_MESSAGE);
    }
}
