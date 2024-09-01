package com.job_test.fake_twitter.application.ports.input;

import com.job_test.fake_twitter.domain.model.User;

import java.util.Set;

public interface UserServicePort {

    User save(String username);

    User findByUsername(String username);

    Set<String> findAllFollowed(String username);

    void follow(String followerUsername, String followedUsername);
}
