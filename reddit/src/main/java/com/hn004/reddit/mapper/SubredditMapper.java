package com.hn004.reddit.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hn004.reddit.Entity.Post;
import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.dto.SubredditDto;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

	@Mapping(target = "numberofPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	SubredditDto mapSubredditToDto(Subreddit subreddit);

	default Integer mapPosts(List<Post> numberOfPosts) {
		return numberOfPosts.size();
	}

	
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDtoToSubreddit(SubredditDto subredditDto);

}
