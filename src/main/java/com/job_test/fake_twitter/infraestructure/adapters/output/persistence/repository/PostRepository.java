package com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository;

import com.job_test.fake_twitter.domain.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Repository
public class PostRepository {

    private final List<Post> posts = new ArrayList<>();

    public Post save(Post post) {
        this.posts.add(post);
        return post;
    }

    public List<Post> findAllByUsername(String username) {
        List<Post> userPosts = new ArrayList<>();
        this.posts.forEach(
                post -> {
                    if (post.getUsername().equals(username)) {
                        userPosts.add(post);
                    }
        });
        if (!userPosts.isEmpty()) userPosts.sort(Comparator.comparing(Post::getDate));
        return userPosts;
    }

    public List<Post> findAllByFollowed(Set<String> followedUsernames) {
        List<Post> allPosts = new ArrayList<>();
        if (followedUsernames.isEmpty()) return allPosts;
        followedUsernames.forEach(
                username -> {
                    allPosts.addAll(findAllByUsername(username));
                });
        if (!allPosts.isEmpty()) allPosts.sort(Comparator.comparing(Post::getDate));
        return allPosts;
    }
}
