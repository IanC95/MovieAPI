package com.ian.movie.model.dao;

import com.ian.movie.model.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document(collection = "comments")
public class CommentDAO {

    @Id
    private String id;

    private String movieId;

    private CommentDataDAO data;

    public CommentDAO() {
    }

    public CommentDAO(CommentDTO commentDTO){
        this.data = new CommentDataDAO(commentDTO.getData());
    }
}
