package com.job_test.fake_twitter.infraestructure.adapters.input;

import com.job_test.fake_twitter.application.ports.input.PostServicePort;
import com.job_test.fake_twitter.domain.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostRestController {

    private final PostServicePort postServicePort;

    @PostMapping("/create")
    public ResponseEntity<Post> save(@RequestBody Post newPost) {
        Post post = this.postServicePort.save(newPost);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/findAll/{username}")
    public ResponseEntity<List<Post>> findAllByUsername(@PathVariable String username) {
        List<Post> posts = this.postServicePort.findAllByUsername(username);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/findAllByFollowed")
    public ResponseEntity<List<Post>> findAllByFollowed(@RequestBody Set<String> followedUsernames) {
        List<Post> posts = this.postServicePort.findAllByFollowed(followedUsernames);
        return ResponseEntity.ok(posts);
    }
}
