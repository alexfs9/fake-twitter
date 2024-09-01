package com.job_test.fake_twitter.application.ports.output;

import com.job_test.fake_twitter.domain.model.User;

import java.util.Optional;
import java.util.Set;

public interface UserPersistencePort {

    User save(String username);

    Optional<User> findByUsername(String username);

    Optional<Set<String>> findAllFollowed(String username);

    void follow(String followerUsername, String followedUsername);
}
