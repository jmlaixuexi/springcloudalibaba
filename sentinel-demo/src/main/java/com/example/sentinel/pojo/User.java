package com.example.sentinel.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    String id;
    String userName;

    public User(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}
