package com.ian.movie.model.dto;

import com.ian.movie.controller.MovieController;
import com.ian.movie.model.dao.CommentDAO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter @Setter
public class CommentDTO{

    private String commentId;
    private CommentDataDTO data;

    public CommentDTO() {
    }

    public CommentDTO(String user, String message, LocalDateTime dateCreated, Integer rating){
        this.data = new CommentDataDTO(user, message, dateCreated, rating);
    }

    public CommentDTO(CommentDAO commentDAO){
        this.commentId = commentDAO.getId();

        Map<String, String> links = new HashMap<>();

        Link selfLink = linkTo(MovieController.class).slash(commentDAO.getMovieId()).slash("comments").slash(this.commentId).withSelfRel();
        links.put(selfLink.getRel(), selfLink.getHref());
        Link movieLink = linkTo(MovieController.class).slash(commentDAO.getMovieId()).withRel("movie");
        links.put(movieLink.getRel(), movieLink.getHref());

        this.data = new CommentDataDTO(commentDAO.getData(), links);
    }
}
