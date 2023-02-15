package com.hn004.reddit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hn004.reddit.Entity.Subreddit;

@Repository
public interface SubredditRepo extends JpaRepository<Subreddit, Long>{

}
