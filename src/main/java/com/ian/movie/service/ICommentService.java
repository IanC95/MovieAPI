package com.ian.movie.service;

import com.ian.movie.exception.CommentValidationException;
import com.ian.movie.exception.NoCommentsException;
import com.ian.movie.exception.NoMovieException;
import com.ian.movie.model.dto.CommentDTO;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ICommentService {
    List<CommentDTO> getComments(String movieId) throws NoMovieException;
    CommentDTO getComment(String movieId, String commentId) throws NoCommentsException, NoMovieException;
    CommentDTO postComment(String movieId, CommentDTO commentDTO) throws CommentValidationException, NoMovieException;
    Pair<CommentDTO, Boolean> putComment(String movieId, String commentId, CommentDTO commentDTO) throws NoMovieException, CommentValidationException;
    List<String> validateCommentDTO(CommentDTO commentDTO);
    void deleteByMovieId(String movieId);
}
