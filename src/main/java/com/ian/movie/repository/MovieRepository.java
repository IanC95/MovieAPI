package com.ian.movie.repository;

import com.ian.movie.model.dao.MovieDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<MovieDAO, String> {
}
