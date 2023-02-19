package com.hn004.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.Repository.CommentRepo;
import com.hn004.reddit.Repository.VoteRepo;
import com.hn004.reddit.Service.AuthService;
import com.hn004.reddit.dto.PostRequest;
import com.hn004.reddit.dto.PostResponse;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepo commentRepository;
    @Autowired
    private VoteRepo voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate",expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
//    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "subredditName",source = "subreddit.subredditName")
    @Mapping(target = "userName", source = "user.username")
    public abstract PostResponse mapToDto(Post post);

//    Integer commentCount(Post post) {
//        return commentRepository.findByPost(post).size();
//    }
//
//    String getDuration(Post post) {
//        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
//    }
//
//    boolean isPostUpVoted(Post post) {
//        return checkVoteType(post, UPVOTE);
//    }
//
//    boolean isPostDownVoted(Post post) {
//        return checkVoteType(post, DOWNVOTE);
//    }
//
//    private boolean checkVoteType(Post post, VoteType voteType) {
//        if (authService.isLoggedIn()) {
//            Optional<Vote> voteForPostByUser =
//                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
//                            authService.getCurrentUser());
//            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
//                    .isPresent();
//        }
//        return false;
//    }

}
