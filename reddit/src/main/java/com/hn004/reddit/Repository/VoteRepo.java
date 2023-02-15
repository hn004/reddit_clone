package com.hn004.reddit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hn004.reddit.Entity.Vote;

@Repository
public interface VoteRepo extends JpaRepository<Vote, Long> {

}
