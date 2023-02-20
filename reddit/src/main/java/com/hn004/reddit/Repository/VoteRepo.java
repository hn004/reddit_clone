package com.hn004.reddit.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.Entity.Vote;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {
     Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post,User currentUser);
}
