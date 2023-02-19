package com.hn004.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hn004.reddit.Entity.Comment;
import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.User;
import com.hn004.reddit.dto.CommentsDto;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "id",ignore = true)
	@Mapping(target = "text",source = "commentsDto.text")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target="post",source="post")
	Comment map(CommentsDto commentsDto, Post post, User user);
	
	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);
	

}
