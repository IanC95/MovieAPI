package com.ian.movie.model.dto;

import com.ian.movie.model.dao.MovieDAO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter @Setter
public class MovieDTO {


    private String movieId;

    @NotNull
    private MovieDataDTO data;

    public MovieDTO() {
    }

    public MovieDTO(String title, String description, String producer, Boolean available_in_3d, String age_rating, Integer rating) {
        this.data = new MovieDataDTO(title, description, producer, available_in_3d, age_rating, rating);
    }

    public MovieDTO(MovieDAO movieDAO){
        this.movieId = movieDAO.getId();
        this.data = new MovieDataDTO(movieDAO.getData(), movieId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(getMovieId(), movieDTO.getMovieId()) &&
                Objects.equals(getData(), movieDTO.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMovieId(), getData());
    }
}
