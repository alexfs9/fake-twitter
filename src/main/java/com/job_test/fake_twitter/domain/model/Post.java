package com.job_test.fake_twitter.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    private String message;
    private LocalDateTime date;
    private String username;
}
