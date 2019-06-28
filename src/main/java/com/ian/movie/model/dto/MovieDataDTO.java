package com.ian.movie.model.dto;

import com.ian.movie.controller.MovieController;
import com.ian.movie.model.dao.MovieDataDAO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Getter @Setter
public class MovieDataDTO {

    private String title;
    private String description;
    private String producer;
    private Boolean available_in_3d;
    private String age_rating;
    private Integer rating;
    private Map<String, Object> links;

    public MovieDataDTO() {
    }

    public MovieDataDTO(String title, String description, String producer, Boolean available_in_3d, String age_rating, Integer rating) {
        this.title = title;
        this.description = description;
        this.producer = producer;
        this.available_in_3d = available_in_3d;
        this.age_rating = age_rating;
        this.rating = rating;
    }

    public MovieDataDTO(MovieDataDAO movieDataDAO, String movieId) {
        this.title = movieDataDAO.getTitle();
        this.description = movieDataDAO.getDescription();
        this.producer = movieDataDAO.getProducer();
        this.available_in_3d = movieDataDAO.getAvailable_in_3d();
        this.age_rating = movieDataDAO.getAge_rating();
        this.rating = movieDataDAO.getRating();

        this.links = new HashMap<>();

        Link selfLink = linkTo(MovieController.class).slash(movieId).withSelfRel();

        List<String> comments = new ArrayList<>();

        this.links.put(selfLink.getRel(), selfLink.getHref());
    }
}
