package com.job_test.fake_twitter.application.service;

import com.job_test.fake_twitter.application.ports.input.PostServicePort;
import com.job_test.fake_twitter.application.ports.output.PostPersistencePort;
import com.job_test.fake_twitter.domain.model.Post;
import com.job_test.fake_twitter.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService implements PostServicePort {

    private final PostPersistencePort postPersistencePort;
    private final UserService userService;

    @Override
    public Post save(Post post) {
        return this.postPersistencePort.save(post);
    }

    @Override
    public List<Post> findAllByUsername(String username) {
        User user = this.userService.findByUsername(username);
        return this.postPersistencePort.findAllByUsername(user.getUsername());
    }

    @Override
    public List<Post> findAllByFollowed(Set<String> followedUsernames) {
        return this.postPersistencePort.findAllByFollowed(followedUsernames);
    }
}
