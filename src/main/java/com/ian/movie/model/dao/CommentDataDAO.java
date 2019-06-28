package com.ian.movie.model.dao;

import com.ian.movie.model.dto.CommentDataDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentDataDAO {

    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private Integer rating;

    public CommentDataDAO() {
    }

    public CommentDataDAO(CommentDataDTO commentDataDTO) {
        this.user = commentDataDTO.getUser();
        this.message = commentDataDTO.getMessage();
        this.rating = commentDataDTO.getRating();
        this.dateCreated = commentDataDTO.getDateCreated();
    }
}
