package com.hn004.reddit.Repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.dto.PostResponse;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<PostResponse> findByUser(User user);

}
