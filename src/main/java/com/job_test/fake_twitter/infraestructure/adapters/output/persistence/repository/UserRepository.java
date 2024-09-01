package com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository;

import com.job_test.fake_twitter.domain.exception.UserNotFoundException;
import com.job_test.fake_twitter.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public User save(String username) {
        User user = new User(username);
        this.users.add(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        User findedUser = null;
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                findedUser = user;
            }
        }
        return Optional.ofNullable(findedUser);
    }

    public Optional<Set<String>> findAllFollowed(String username) {
        Optional<User> user = this.findByUsername(username);
        Set<String> followedUsers = null;
        if (user.isPresent()) {
            followedUsers = new HashSet<>(user.get().getFollowed());
        }
        return Optional.ofNullable(followedUsers);
    }

    public void follow(String followerUsername, String followedUsername) {
        Optional<User> followerUser = this.findByUsername(followerUsername);
        Optional<User> followedUser = this.findByUsername(followedUsername);

        if (followerUser.isPresent() && followedUser.isPresent()) {
            followedUser.get().getFollowers().add(followerUser.get().getUsername());
            followerUser.get().getFollowed().add(followedUser.get().getUsername());

            replaceUser(followedUser.get());
            replaceUser(followerUser.get());
        } else {
            if (followerUser.isEmpty())
                throw new UserNotFoundException("No se encontró un usuario llamado @" + followerUsername);
            throw new UserNotFoundException("No se encontró un usuario llamado @" + followedUsername);
        }
    }

    public void replaceUser(User user) {
        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(user.getUsername())) {
                this.users.set(i, user);
                return;
            }
        }
    }
}
