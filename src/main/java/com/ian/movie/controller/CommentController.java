package com.ian.movie.controller;

import com.ian.movie.exception.CommentValidationException;
import com.ian.movie.exception.NoCommentsException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.model.dto.CommentDTO;
import com.ian.movie.model.dto.MovieDTO;
import com.ian.movie.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/movies/{movieId}/comments")
public class CommentController {

    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping()
    public List<CommentDTO> getComments(@PathVariable String movieId) throws NoCommentsException, NoMovieException {
        return commentService.getComments(movieId);
    }

    @GetMapping("/{commentId}")
    public CommentDTO getComment(@PathVariable String movieId, @PathVariable String commentId) throws NoCommentsException, NoMovieException {
        return commentService.getComment(movieId, commentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO postComment(@PathVariable String movieId, @Valid @RequestBody CommentDTO commentDTO) throws CommentValidationException, NoMovieException {
        return this.commentService.postComment(movieId, commentDTO);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> putMovie(@PathVariable String movieId, @PathVariable String commentId, @Valid @RequestBody CommentDTO commentDTO) throws NoMovieException, CommentValidationException {
        Pair<CommentDTO, Boolean> result = this.commentService.putComment(movieId, commentId, commentDTO);

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
}
