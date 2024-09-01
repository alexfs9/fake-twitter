package com.job_test.fake_twitter;

import com.job_test.fake_twitter.domain.model.Post;
import com.job_test.fake_twitter.domain.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@SpringBootApplication
public class FakeTwitterApplication implements CommandLineRunner {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String userApiUrl = "http://localhost:8080/user/";
	private final String postApiUrl = "http://localhost:8080/post/";
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	public static void main(String[] args) {
		SpringApplication.run(FakeTwitterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String command;

		while (true) {
			command = scanner.nextLine();
			if (command.contains("@")) {
				if (command.contains("post")) {
					post(command);
				} else if (command.contains("follow")) {
					follow(command);
				} else if (command.contains("dashboard")) {
					dashboard(command);
				} else if (command.contains("wall")) {
					wall(command);
				}
			}
		}
	}

	private void post(String command) {
		String[] words = command.split(" ");
		String username = words[1].substring(1);

		StringBuilder message = new StringBuilder(words[2]);
		if (words.length > 3) {
			for (int i = 3; i < words.length; i++) {
				message.append(" ").append(words[i]);
			}
		}

		try {
			ResponseEntity<User> response = restTemplate
					.getForEntity(userApiUrl + "/find/{username}", User.class, username);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				User user = restTemplate
						.postForEntity(userApiUrl + "/create", new User(username), User.class)
						.getBody();
			}
		}

		Post post = restTemplate
				.postForEntity(
						postApiUrl + "/create",
						new Post(message.toString(),
								LocalDateTime.now(),
								username),
						Post.class)
				.getBody();

		System.out.println(username + " posted -> \""
				+ post.getMessage() + "\" @"
				+ post.getDate().format(formatter));

	}

	private void follow(String command) {
		String followerUsername = command.split(" ")[1].substring(1);
		String followedUsername = command.split(" ")[2].substring(1);
		HashMap<String, String> usernames = new HashMap<>();
		usernames.put("followerUsername", followerUsername);
		usernames.put("followedUsername", followedUsername);
		try {
			String message = restTemplate
					.getForEntity(
							userApiUrl + "/follow/{followerUsername}/{followedUsername}",
							String.class,
							usernames)
					.getBody();
			System.out.println(message);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				System.out.println(ex.getResponseBodyAsString());
			}
		}
	}

	private void wall(String command) {
		String username = command.split(" ")[1].substring(1);

		User user = this.userExists(username);

		if (user != null) {
			ResponseEntity<List<Post>> response = restTemplate.exchange(
					postApiUrl + "/findAll/{username}",
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Post>>() {},
					username
			);

			showPosts(response, "El usuario @" + username + " no ha hecho posts.");
		}
	}

	private void dashboard(String command) {
		String username = command.split(" ")[1].substring(1);

		User user = userExists(username);

		if (user != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			HttpEntity<Set<String>> requestEntity = new HttpEntity<>(user.getFollowed(), headers);
			ResponseEntity<List<Post>> response = restTemplate.exchange(
					postApiUrl + "/findAllByFollowed",
					HttpMethod.POST,
					requestEntity,
					new ParameterizedTypeReference<List<Post>>() {}
			);

			showPosts(response, "No hay posts para mostrar.");
		}
	}

	private User userExists(String username) {
		User user = null;
		try {
			ResponseEntity<User> response = this.restTemplate
					.getForEntity(this.userApiUrl + "/find/{username}", User.class, username);
			user = response.getBody();
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				System.out.println(ex.getResponseBodyAsString());
			}
		}
		return user;
	}

	private void showPosts(ResponseEntity<List<Post>> response, String possibleMessage) {
		if (!response.getBody().isEmpty()) {
			response.getBody().forEach(
					post -> {
						System.out.println("\"" + post.getMessage() + "\" @"
								+ post.getUsername() + " @"
								+ post.getDate().format(this.formatter));
					});
		} else {
			System.out.println(possibleMessage);
		}
	}
}
