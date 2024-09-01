package com.job_test.fake_twitter.infraestructure.adapters.output.persistence;

import com.job_test.fake_twitter.domain.model.User;
import com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserPersistenceAdapterTest {

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        User newUser = new User("alexander");
        Mockito.when(this.userRepository.save(Mockito.any(String.class))).thenReturn(newUser);

        User savedUser = this.userPersistenceAdapter.save("alexander");

        assertEquals("alexander", savedUser.getUsername());
    }

    @Test
    void findByUsername() {
        User savedUser = new User("alexander");
        Mockito.when(this.userRepository.findByUsername("alexander")).thenReturn(Optional.of(savedUser));

        String username = "alexander";
        User user = null;
        Optional<User> userObject = this.userPersistenceAdapter.findByUsername(username);
        if (userObject.isPresent()) {
            user = userObject.get();
        }

        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }

    @Test
    void findAllFollowed() {
        Set<String> savedFollowedUsernames = Set.of("alexander", "adrian");
        Mockito.when(this.userRepository.findAllFollowed("javier"))
                .thenReturn(Optional.of(savedFollowedUsernames));

        String username = "javier";
        Optional<Set<String>> usernames = this.userPersistenceAdapter.findAllFollowed(username);
        Set<String> followedUsernames = null;
        if (usernames.isPresent()) {
            followedUsernames = usernames.get();
        }

        assertNotNull(followedUsernames);
        assertEquals(followedUsernames, savedFollowedUsernames);
    }

    @Test
    void follow() {
    }
}