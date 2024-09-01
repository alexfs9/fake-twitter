package com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository;

import com.job_test.fake_twitter.domain.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest {

    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        this.postRepository = new PostRepository();
    }

    @Test
    void save() {
        Post post = new Post("hola mundo", LocalDateTime.now(), "lucho");
        Post savedPost = this.postRepository.save(post);

        assertNotNull(savedPost);
        assertEquals(post, savedPost);
        assertTrue(this.postRepository.findAllByUsername("lucho").contains(post));
    }

    @Test
    void findAllByUsername() {
        Post post1 = new Post("primer post", LocalDateTime.now().minusDays(1), "lucho");
        Post post2 = new Post("segundo post", LocalDateTime.now(), "lucho");
        Post post3 = new Post("post de otro usuario", LocalDateTime.now(), "otro");

        this.postRepository.save(post1);
        this.postRepository.save(post2);
        this.postRepository.save(post3);

        List<Post> userPosts = this.postRepository.findAllByUsername("lucho");

        assertEquals(2, userPosts.size());
        assertEquals(post1, userPosts.get(0));
        assertEquals(post2, userPosts.get(1));
    }

    @Test
    void findAllByUsernameEmptyList() {
        List<Post> userPosts = postRepository.findAllByUsername("no-existe");

        assertTrue(userPosts.isEmpty());
    }

    @Test
    void findAllByFollowed() {
        Post post1 = new Post("post de lucho", LocalDateTime.now().minusDays(2), "lucho");
        Post post2 = new Post("post de alberto", LocalDateTime.now().minusDays(1), "alberto");
        Post post3 = new Post("post de lucho", LocalDateTime.now(), "lucho");

        this.postRepository.save(post1);
        this.postRepository.save(post2);
        this.postRepository.save(post3);

        Set<String> followedUsernames = Set.of("lucho", "alberto");
        List<Post> posts = this.postRepository.findAllByFollowed(followedUsernames);

        assertEquals(3, posts.size());
        assertEquals(post1, posts.get(0));
        assertEquals(post2, posts.get(1));
        assertEquals(post3, posts.get(2));
    }

    @Test
    void findAllByFollowedEmptySet() {
        List<Post> posts = this.postRepository.findAllByFollowed(Set.of());

        assertTrue(posts.isEmpty());
    }

    @Test
    void findAllByFollowedNoPosts() {
        Set<String> followedUsernames = Set.of("lucho", "alberto");
        List<Post> posts = this.postRepository.findAllByFollowed(followedUsernames);

        assertTrue(posts.isEmpty());
    }
}