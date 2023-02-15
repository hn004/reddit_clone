package com.hn004.reddit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hn004.reddit.Entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long>{

}
