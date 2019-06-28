package com.ian.movie.model.dao;

import com.ian.movie.model.dto.MovieDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter @Setter
@Document(collection = "movies")
public class MovieDAO {

    @Id
    private String id;

    private MovieDataDAO data;

    public MovieDAO() {
    }

    public MovieDAO(MovieDTO movieDTO){
        this.data = new MovieDataDAO(movieDTO.getData());
    }
}
