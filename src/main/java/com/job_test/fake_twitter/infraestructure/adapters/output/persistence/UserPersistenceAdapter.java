package com.job_test.fake_twitter.infraestructure.adapters.output.persistence;

import com.job_test.fake_twitter.application.ports.output.UserPersistencePort;
import com.job_test.fake_twitter.domain.model.User;
import com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    @Override
    public User save(String username) {
        return this.userRepository.save(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<Set<String>> findAllFollowed(String username) {
        return this.userRepository.findAllFollowed(username);
    }

    @Override
    public void follow(String followerUsername, String followedUsername) {
        this.userRepository.follow(followerUsername, followedUsername);
    }
}
