package com.job_test.fake_twitter.application.service;

import com.job_test.fake_twitter.application.ports.input.UserServicePort;
import com.job_test.fake_twitter.application.ports.output.UserPersistencePort;
import com.job_test.fake_twitter.domain.exception.UserNotFoundException;
import com.job_test.fake_twitter.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public User save(String username) {
        return this.userPersistencePort.save(username);
    }

    @Override
    public User findByUsername(String username) {
        return this.userPersistencePort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("El usuario @" + username + " no existe."));
    }

    @Override
    public Set<String> findAllFollowed(String username) {
        return this.userPersistencePort.findAllFollowed(username)
                .orElseThrow(() -> new UserNotFoundException("El usuario @" + username + " no existe."));
    }

    @Override
    public void follow(String followerUsername, String followedUsername) {
        this.userPersistencePort.follow(followerUsername, followedUsername);
    }
}
