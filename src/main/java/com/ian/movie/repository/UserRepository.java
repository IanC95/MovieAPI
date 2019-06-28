package com.ian.movie.repository;

import com.ian.movie.model.dao.UserDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDAO, String> {
}
