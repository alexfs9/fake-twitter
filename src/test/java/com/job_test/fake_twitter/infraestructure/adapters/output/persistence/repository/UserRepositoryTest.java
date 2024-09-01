package com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository;

import com.job_test.fake_twitter.domain.exception.UserNotFoundException;
import com.job_test.fake_twitter.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        this.userRepository = new UserRepository();
    }

    @Test
    void save() {
        User user = this.userRepository.save("alexander");
        assertNotNull(user);
        assertEquals("alexander", user.getUsername());
        assertTrue(this.userRepository.findByUsername("alexander").isPresent());
    }

    @Test
    void findByUsername() {
        this.userRepository.save("alexander");
        Optional<User> user = this.userRepository.findByUsername("alexander");
        assertTrue(user.isPresent());
        assertEquals("alexander", user.get().getUsername());
    }

    @Test
    void findByUsernameNotFound() {
        Optional<User> user = this.userRepository.findByUsername("random");
        assertFalse(user.isPresent());
    }

    @Test
    void findAllFollowed() {
        User user = this.userRepository.save("alexander");
        user.getFollowed().add("adrian");
        Optional<Set<String>> followed = this.userRepository.findAllFollowed("alexander");
        assertTrue(followed.isPresent());
        assertTrue(followed.get().contains("adrian"));
    }

    @Test
    void findAllFollowedForNonExistentUser() {
        Optional<Set<String>> followed = this.userRepository.findAllFollowed("random");
        assertFalse(followed.isPresent());
    }

    @Test
    void followSuccess() {
        User follower = this.userRepository.save("alexander");
        User followed = this.userRepository.save("adrian");

        this.userRepository.follow("alexander", "adrian");

        assertTrue(followed.getFollowers().contains("alexander"));
        assertTrue(follower.getFollowed().contains("adrian"));
    }

    @Test
    void followNonExistentUser() {
        this.userRepository.save("alexander");

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            this.userRepository.follow("alexander", "random");
        });

        assertEquals("No se encontró un usuario llamado @random", exception.getMessage());
    }

    @Test
    void replaceUser() {
        User user = this.userRepository.save("alexander");
        User updatedUser = new User("alexander");
        updatedUser.getFollowers().add("adrian");

        this.userRepository.replaceUser(updatedUser);
        Optional<User> savedUser = this.userRepository.findByUsername("alexander");

        assertNotEquals(user, savedUser.get());
    }
}