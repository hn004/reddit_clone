package com.hn004.reddit.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hn004.reddit.Service.CommentService;
import com.hn004.reddit.dto.CommentsDto;

import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto) {

		commentService.createComment(commentsDto);
		return new ResponseEntity<>(CREATED);
	}

	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
		return status(OK).body(commentService.getAllCommentsForPost(postId));
	}

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable String userName) {
        return status(OK).body(commentService.getCommentsForUser(userName));
    }

}
