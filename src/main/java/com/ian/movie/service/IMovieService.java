package com.ian.movie.service;

import com.ian.movie.exception.MovieValidationException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.exception.PageValidationException;
import com.ian.movie.model.dto.MovieDTO;
import org.springframework.data.util.Pair;

import java.util.List;

public interface IMovieService {
    boolean movieExists(String id);
    MovieDTO getMovie(String id) throws NoMovieException;
    List<MovieDTO> getMovies(String page, String size)throws PageValidationException;
    MovieDTO postMovie(MovieDTO movieDTO) throws MovieValidationException;
    Pair<MovieDTO, Boolean> putMovie(String movieId, MovieDTO updateMovieDTO) throws MovieValidationException;
    List<String> validateMovieDTO(MovieDTO movieDTO);
    void deleteMovie(String id) throws NoMovieException;
}
