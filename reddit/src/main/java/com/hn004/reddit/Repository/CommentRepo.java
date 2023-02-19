package com.hn004.reddit.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hn004.reddit.Entity.Comment;
import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.User;

public interface CommentRepo extends JpaRepository<Comment, Long>{
 
	List<Comment> findByPost(Post post);
	List<Comment> findAllByUser(User user);
}
