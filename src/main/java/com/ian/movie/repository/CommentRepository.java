package com.ian.movie.repository;

import com.ian.movie.model.dao.CommentDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<CommentDAO, String> {
    List<CommentDAO> findByMovieId(String movieId);
    Optional<CommentDAO> findByIdAndMovieId(String commentId, String MovieId);
}
