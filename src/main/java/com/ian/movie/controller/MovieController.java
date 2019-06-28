package com.ian.movie.controller;

import com.ian.movie.exception.MovieValidationException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.exception.PageValidationException;
import com.ian.movie.exception.UserNotValidatedException;
import com.ian.movie.model.dto.MovieDTO;
import com.ian.movie.service.IMovieService;
import com.ian.movie.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    private final IMovieService movieService;

    private final IUserService userService;

    @Autowired
    public MovieController(IMovieService movieService, IUserService userService){
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MovieDTO> getMovies(@RequestParam(name = "page", defaultValue = "0") String page,
                                    @RequestParam(name = "size", defaultValue = "20") String size)
            throws PageValidationException {
        return this.movieService.getMovies(page, size);
    }

    @GetMapping("/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public MovieDTO getMovie(@PathVariable String movieId) throws NoMovieException {
        return this.movieService.getMovie(movieId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieDTO postMovie(@Valid @RequestBody MovieDTO movieDTO) throws MovieValidationException {
        return this.movieService.postMovie(movieDTO);
    }

    @PutMapping("/{movieId}")
    public ResponseEntity<MovieDTO> putMovie(@PathVariable String movieId,
                                             @Valid @RequestBody MovieDTO movieDTO) throws MovieValidationException{
        Pair<MovieDTO, Boolean> result = this.movieService.putMovie(movieId, movieDTO);

        if(result.getSecond()){
            return new ResponseEntity<>(
                    result.getFirst(),
                    HttpStatus.OK
            );
        }else{
            return new ResponseEntity<>(
                    result.getFirst(),
                    HttpStatus.CREATED
            );
        }
    }

    @DeleteMapping("/{movieId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable String movieId,
                            @RequestHeader(value = "userName", required = false) String userName,
                            @RequestHeader(value = "authCode", required = false) String authCode)throws NoMovieException, UserNotValidatedException {
        if(this.userService.userAuthenticated(userName, authCode)){
            this.movieService.deleteMovie(movieId);
        }
    }

}
