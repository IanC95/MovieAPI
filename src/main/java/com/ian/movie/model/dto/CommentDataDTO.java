package com.ian.movie.model.dto;

import com.ian.movie.model.dao.CommentDataDAO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter @Setter
public class CommentDataDTO {

    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private Integer rating;
    private Map<String, String> links;

    public CommentDataDTO() {
    }

    public CommentDataDTO(String user, String message, LocalDateTime dateCreated, Integer rating) {
        this.user = user;
        this.message = message;
        this.dateCreated = dateCreated;
        this.rating = rating;
    }

    public CommentDataDTO(CommentDataDAO commentDataDAO, Map<String, String> links){
        this.user = commentDataDAO.getUser();
        this.message = commentDataDAO.getMessage();
        this.dateCreated = commentDataDAO.getDateCreated();
        this.rating = commentDataDAO.getRating();
        this.links = links;
    }
}
