package com.ian.movie.service.impl;

import com.ian.movie.controller.MovieController;
import com.ian.movie.exception.MovieValidationException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.exception.PageValidationException;
import com.ian.movie.model.dao.MovieDAO;
import com.ian.movie.model.dto.CommentDTO;
import com.ian.movie.model.dto.MovieDTO;
import com.ian.movie.model.dto.MovieDataDTO;
import com.ian.movie.repository.MovieRepository;
import com.ian.movie.service.ICommentService;
import com.ian.movie.service.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Service
public class MovieServiceImpl implements IMovieService {

    private final MovieRepository movieRepository;
    private final ICommentService commentService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, @Lazy ICommentService commentService) {
        this.movieRepository = movieRepository;
        this.commentService = commentService;
    }

    @Override
    public boolean movieExists(String id) {
        return movieRepository.existsById(id);
    }

    @Override
    public MovieDTO getMovie(String id) throws NoMovieException {
        Optional<MovieDAO> optionalMovieDAO = movieRepository.findById(id);

        MovieDAO movieDAO= optionalMovieDAO.orElseThrow(() -> new NoMovieException(id));
        MovieDTO movieDTO = new MovieDTO(movieDAO);
        linkAdder(movieDTO);
        return movieDTO;
    }

    @Override
    public MovieDTO postMovie(MovieDTO movieDTO) throws MovieValidationException {
        List<String> errors = validateMovieDTO(movieDTO);

        if(!errors.isEmpty()){
            throw new MovieValidationException(errors);
        }

        MovieDAO movieDAO = new MovieDAO(movieDTO);
        MovieDTO returnMovieDTO = new MovieDTO(movieRepository.save(movieDAO));
        linkAdder(returnMovieDTO);
        return returnMovieDTO;
    }

    @Override
    public Pair<MovieDTO, Boolean> putMovie(String movieId, MovieDTO movieDTO) throws MovieValidationException {
        List<String> errors = validateMovieDTO(movieDTO);

        if(!errors.isEmpty()){
            throw new MovieValidationException(errors);
        }

        Boolean movieExists = movieRepository.existsById(movieId);

        MovieDAO movieDAO = new MovieDAO(movieDTO);
        movieDAO.setId(movieId);
        MovieDTO movieDTONew = new MovieDTO(movieRepository.save(movieDAO));
        linkAdder(movieDTONew);
        return Pair.of(movieDTONew, movieExists);
    }

    @Override
    public List<MovieDTO> getMovies(String page, String size) throws PageValidationException{
        List<String> errors = parametersValidation(page, size);

        if(!errors.isEmpty()){
            throw new PageValidationException(errors);
        }

        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        Page<MovieDAO> moviePage = movieRepository.findAll(pageable);
        List<MovieDAO> movieDAOList = moviePage.getContent();

        if(movieDAOList.isEmpty()){
            return new ArrayList<>();
        }

        List<MovieDTO> movieDTOList = new ArrayList<>();

        for(MovieDAO movieDAO : movieDAOList){
            MovieDTO movieDTO = new MovieDTO(movieDAO);
            linkAdder(movieDTO);
            movieDTOList.add(movieDTO);
        }

        return movieDTOList;
    }

    @Override
    public void deleteMovie(String id) throws NoMovieException {
        if(this.movieExists(id)){
            this.movieRepository.deleteById(id);
            this.commentService.deleteByMovieId(id);
        }else{
            throw new NoMovieException(id);
        }
    }

    @Override
    public List<String> validateMovieDTO(MovieDTO movieDTO){
        List<String> errors = new ArrayList<>();
        MovieDataDTO movieDataDTO = movieDTO.getData();

        if(movieDataDTO == null){
            errors.add("Data cannot be null");
        }else {

            if (movieDataDTO.getTitle() == null) {
                errors.add("title not provided");
            }

            if (movieDataDTO.getAvailable_in_3d() == null) {
                errors.add("available_in_3d not provided");
            }

            if (movieDataDTO.getAge_rating() == null) {
                errors.add("age_rating not provided");
            }

            if (movieDataDTO.getDescription() == null) {
                errors.add("description not provided");
            }

            if (movieDataDTO.getProducer() == null) {
                errors.add("producer not provided");
            }

            if (movieDataDTO.getRating() == null) {
                errors.add("rating not provided");
            }

        }

        return errors;
    }

    private void linkAdder(MovieDTO movieDTO){
        List<CommentDTO> comments;
        try {
            comments = commentService.getComments(movieDTO.getMovieId());
        }catch(NoMovieException e){
            comments = new ArrayList<>();
        }
        Map<String, Object> links = movieDTO.getData().getLinks();
        List<String> commentsLinkList = new ArrayList<>();

        for(CommentDTO commentDTO : comments){
            Link commentLink = linkTo(MovieController.class).slash(movieDTO.getMovieId()).slash("comments").slash(commentDTO.getCommentId()).withRel("comments");
            commentsLinkList.add(commentLink.getHref());
        }

        links.put("comments", commentsLinkList);
    }

    private List<String> parametersValidation(String page, String size){
        List<String> errors = new ArrayList<>();

        if(!isInteger(page)){
            errors.add("Page is not an integer");
        }else if(Integer.parseInt(page) < 0){
            errors.add("Page cannot be below 0");
        }

        if(!isInteger(size)){
            errors.add("size is not an integer");
        }else if(Integer.parseInt(size) <= 0){
            errors.add("Size must be a positive integer");
        }else if(Integer.parseInt(size) > 20){
            errors.add("Size cannot be larger than 20");
        }

        return errors;
    }

    private boolean isInteger(String s){
        if(s == null || s.isEmpty()){
            return false;
        }

        try(Scanner sc = new Scanner(s.trim())) {

            if (!sc.hasNextInt()) {
                return false;
            }

            sc.nextInt();
            return !sc.hasNext();
        }
    }
}
