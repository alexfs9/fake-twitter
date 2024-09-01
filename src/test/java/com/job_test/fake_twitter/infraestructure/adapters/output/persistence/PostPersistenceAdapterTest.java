package com.job_test.fake_twitter.infraestructure.adapters.output.persistence;

import com.job_test.fake_twitter.domain.model.Post;
import com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PostPersistenceAdapterTest {

    @InjectMocks
    private PostPersistenceAdapter postPersistenceAdapter;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
        Post newPost = new Post("hola mundo", LocalDateTime.now(), "alexander");
        Mockito.when(this.postRepository.save(Mockito.any(Post.class))).thenReturn(newPost);

        Post savedPost = this.postPersistenceAdapter.save(newPost);
        assertEquals(savedPost, newPost);
    }

    @Test
    void findAllByUsername() {
        Post postOne = new Post("hola mundo", LocalDateTime.now(), "alexander");
        Post postTwo = new Post("java", LocalDateTime.now(), "alexander");
        List<Post> posts = new ArrayList<>(List.of(postOne, postTwo));
        Mockito.when(this.postRepository.findAllByUsername("alexander")).thenReturn(posts);

        List<Post> userPosts = this.postPersistenceAdapter.findAllByUsername("alexander");
        assertEquals(userPosts, posts);
    }

    @Test
    void findAllByFollowed() {
        Post postOne = new Post("hola mundo", LocalDateTime.now(), "lucho");
        Post postTwo = new Post("java", LocalDateTime.now(), "alberto");
        List<Post> posts = new ArrayList<>(List.of(postOne, postTwo));
        Mockito.when(this.postRepository.findAllByFollowed(Set.of("lucho", "alberto"))).thenReturn(posts);

        List<Post> usersPosts = this.postPersistenceAdapter.findAllByFollowed(Set.of("lucho", "alberto"));
        assertEquals(usersPosts, posts);
    }
}