package com.job_test.fake_twitter.domain.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    //private Long id;
    private String username;
    private Set<String> followers;
    private Set<String> followed;

    public User(String username) {
        this.username = username;
        this.followers = new HashSet<>();
        this.followed = new HashSet<>();
    }
}
