package com.hn004.reddit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Service.PostService;
import com.hn004.reddit.dto.PostRequest;
import com.hn004.reddit.dto.PostResponse;

import lombok.AllArgsConstructor;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

	@Autowired
	public PostService postService;

	@PostMapping
	public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {

		return new ResponseEntity<Post>(postService.save(postRequest), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PostResponse>> getAllPosts() {
		return status(HttpStatus.OK).body(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
		return status(HttpStatus.OK).body(postService.getPost(id));
	}

	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long id) {
		return status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
	}

	@GetMapping("by-user/{name}")
	public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
		return status(HttpStatus.OK).body(postService.getPostByUsername(username));
	}

}
