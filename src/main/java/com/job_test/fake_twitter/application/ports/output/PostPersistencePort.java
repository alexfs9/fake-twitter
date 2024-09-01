package com.job_test.fake_twitter.application.ports.output;

import com.job_test.fake_twitter.domain.model.Post;

import java.util.List;
import java.util.Set;

public interface PostPersistencePort {

    Post save(Post post);

    List<Post> findAllByUsername(String username);

    List<Post> findAllByFollowed(Set<String> followedUsernames);
}
