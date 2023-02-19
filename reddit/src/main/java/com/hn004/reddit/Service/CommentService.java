package com.hn004.reddit.Service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.Comment;
import com.hn004.reddit.Entity.NotificationEmail;
import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.Repository.CommentRepo;
import com.hn004.reddit.Repository.PostRepo;
import com.hn004.reddit.Repository.UserRepo;
import com.hn004.reddit.Service.CustomException.PostNotFoundException;
import com.hn004.reddit.dto.CommentsDto;
import com.hn004.reddit.mapper.CommentMapper;

import lombok.AllArgsConstructor;
import static java.util.stream.Collectors.toList;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

	private final PostRepo postRepo;
	private final UserRepo userRepo;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepo commentRepo;
	private final MailService mailService;
	private final MailContentBuilder mailContentBuilder;
	private static final String POST_URL = "";

	public void createComment(CommentsDto commentsDto) {
		Post post = postRepo.findById(commentsDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepo.save(comment);
		String message = mailContentBuilder.build(post.getUser().getUsername() + "commented on your post" + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + "made comment on your post", user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new PostNotFoundException("post with" + postId + "not found"));
		return commentRepo.findByPost(post).stream().map(commentMapper::mapToDto).collect(toList());
	}

	public List<CommentsDto> getCommentsForUser(String userName) {
		User user = userRepo.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(userName.toString()));
		return commentRepo.findAllByUser(user).stream().map(commentMapper::mapToDto).collect(toList());
	}

}
