package com.ian.movie.model.dao;

import com.ian.movie.model.dto.MovieDataDTO;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieDataDAO {

    private String title;
    private String description;
    private String producer;
    private Boolean available_in_3d;
    private String age_rating;
    private Integer rating;

    public MovieDataDAO() {
    }

    public MovieDataDAO(MovieDataDTO movieDataDTO) {
        this.title = movieDataDTO.getTitle();
        this.description = movieDataDTO.getDescription();
        this.producer = movieDataDTO.getProducer();
        this.available_in_3d = movieDataDTO.getAvailable_in_3d();
        this.age_rating = movieDataDTO.getAge_rating();
        this.rating = movieDataDTO.getRating();
    }
}
