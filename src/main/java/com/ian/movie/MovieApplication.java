package com.ian.movie;

import com.ian.movie.model.dao.CommentDAO;
import com.ian.movie.model.dao.MovieDAO;
import com.ian.movie.model.dao.UserDAO;
import com.ian.movie.model.dto.CommentDTO;
import com.ian.movie.model.dto.MovieDTO;
import com.ian.movie.repository.CommentRepository;
import com.ian.movie.repository.MovieRepository;
import com.ian.movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;

@SpringBootApplication
public class MovieApplication implements CommandLineRunner {

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
    UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(MovieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		this.movieRepository.deleteAll();
		this.commentRepository.deleteAll();
		this.userRepository.deleteAll();

		MovieDTO movieDTO = new MovieDTO("Movie1", "Movie1 desc", "Michael Bay", true, "R", 5);
		MovieDAO movieDAO = new MovieDAO(movieDTO);
		movieDAO.setId("5d03a83d5f959fa2f17e2d2e");
		CommentDTO commentDTO = new CommentDTO("User", "message", LocalDateTime.now(), 5);
		CommentDAO commentDAO = new CommentDAO(commentDTO);
		commentDAO.setMovieId("5d03a83d5f959fa2f17e2d2e");

		this.movieRepository.save(movieDAO);
		this.commentRepository.save(commentDAO);

        this.userRepository.save(new UserDAO("Ian", "1234567890"));
        */
	}
}
