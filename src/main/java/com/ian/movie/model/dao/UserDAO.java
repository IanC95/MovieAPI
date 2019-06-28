package com.ian.movie.model.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document(collection = "users")
public class UserDAO {
    @Id
    private String userName;
    private int authHash;

    public UserDAO(String userName, String authCode) {
        this.userName = userName;
        //TODO: use crypto safe hash
        this.authHash = authCode.hashCode();
    }

    public UserDAO(String userName, int authHash) {
        this.userName = userName;
        this.authHash = authHash;
    }

    public UserDAO() {
    }
}
