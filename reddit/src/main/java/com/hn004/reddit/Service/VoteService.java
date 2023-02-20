package com.hn004.reddit.Service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.Vote;
import com.hn004.reddit.Entity.VoteType;
import com.hn004.reddit.Repository.PostRepo;
import com.hn004.reddit.Repository.VoteRepo;
import com.hn004.reddit.Service.CustomException.PostNotFoundException;
import com.hn004.reddit.Service.CustomException.StringRedditException;
import com.hn004.reddit.dto.VoteDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

	private final PostRepo postRepo;
	private final VoteRepo voteRepo;
	private final AuthService authService;

//	VoteType UPVoTE;

	public void vote(VoteDto voteDto) {
		Post post = postRepo.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException("Post Not found"));
		Optional<Vote> voteByPostAndUser = voteRepo.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteByPostAndUser)) {
			throw new StringRedditException("you have already " + voteDto.getVoteType() + "d for this post");
		}
		if(VoteType.UPVoTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount()+1);
		}else {
			post.setVoteCount(post.getVoteCount()-1);
		}
		
		voteRepo.save(mapToVote(voteDto,post));
		postRepo.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder().voteType(voteDto.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}

}
