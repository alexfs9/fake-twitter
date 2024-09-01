package com.job_test.fake_twitter.infraestructure.adapters.output.persistence;

import com.job_test.fake_twitter.application.ports.output.PostPersistencePort;
import com.job_test.fake_twitter.domain.model.Post;
import com.job_test.fake_twitter.infraestructure.adapters.output.persistence.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostPersistenceAdapter implements PostPersistencePort {

    private final PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return this.postRepository.save(post);
    }

    @Override
    public List<Post> findAllByUsername(String username) {
        return this.postRepository.findAllByUsername(username);
    }

    @Override
    public List<Post> findAllByFollowed(Set<String> followedUsernames) {
        return this.postRepository.findAllByFollowed(followedUsernames);
    }
}
