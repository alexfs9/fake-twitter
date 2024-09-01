package com.job_test.fake_twitter.infraestructure.adapters.input;

import com.job_test.fake_twitter.application.ports.input.UserServicePort;
import com.job_test.fake_twitter.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServicePort userServicePort;

    @PostMapping("/create")
    public ResponseEntity<User> save(@RequestBody User newUser) {
        User user = this.userServicePort.save(newUser.getUsername());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        User user = this.userServicePort.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("follow/{followerUsername}/{followedUsername}")
    public ResponseEntity<String> follow(@PathVariable String followerUsername, @PathVariable String followedUsername) {
        this.userServicePort.follow(followerUsername, followedUsername);
        return ResponseEntity.ok(followerUsername + " empezó a seguir a " + followedUsername);
    }
}
