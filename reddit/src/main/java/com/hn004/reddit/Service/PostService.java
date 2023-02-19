package com.hn004.reddit.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.Repository.PostRepo;
import com.hn004.reddit.Repository.SubredditRepo;
import com.hn004.reddit.Repository.UserRepo;
import com.hn004.reddit.Service.CustomException.PostNotFoundException;
import com.hn004.reddit.Service.CustomException.SubredditNotFoundException;
import com.hn004.reddit.dto.PostRequest;
import com.hn004.reddit.dto.PostResponse;
import com.hn004.reddit.mapper.PostMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

	@Autowired
	private final PostRepo postRepo;
	@Autowired
	private final SubredditRepo subredditRepo;
	@Autowired
	private final UserRepo userRepo;
	@Autowired
	private final AuthService authService;
	@Autowired
	private final PostMapper postMapper;

//	@Transactional(readOnly = true)
//	public Post save(PostRequest postRequest) {
//		Subreddit subreddit = subredditRepo.findBySubredditName(postRequest.getSubredditName())
//				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
//		return postRepo.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
//	}
	@Transactional
	public Post save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepo.findBySubredditName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		return postRepo.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post=postRepo.findById(id).orElseThrow(()-> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts(){
		return postRepo.findAll().stream().map(postMapper::mapToDto).collect(toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostBySubreddit(Long subredditId){
		Subreddit subreddit=subredditRepo.findById(subredditId).orElseThrow(()-> new SubredditNotFoundException(subredditId.toString()));
		List<Post> posts=postRepo.findAllBySubreddit(subreddit);
		return posts.stream().map(postMapper::mapToDto).collect(toList());
	}
	
	@Transactional(readOnly = true)
	public List<PostResponse> getPostByUsername(String username){
		User user=userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username.toString()));
		return postRepo.findByUser(user);
	}

	

}
