package com.hn004.reddit.mapper;

import org.springframework.context.annotation.Bean;

import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.Entity.Subreddit.SubredditBuilder;
import com.hn004.reddit.dto.SubredditDto;
import com.hn004.reddit.dto.SubredditDto.SubredditDtoBuilder;


public class SubredditMapperImpl implements SubredditMapper {

	@Override
	@Bean
	public SubredditDto mapSubredditToDto(Subreddit subreddit) {
        
		if(subreddit==null) {
			return null;
		}
		SubredditDtoBuilder subredditDto=SubredditDto.builder();
		subredditDto.id(subreddit.getId());
		subredditDto.name(subreddit.getName());
		subredditDto.description(subreddit.getDescription());
		subredditDto.numberofPosts(mapPosts(subreddit.getPosts()));
		return subredditDto.build();
	}

	@Override
	@Bean
	public Subreddit mapDtoToSubreddit(SubredditDto subredditDto) {
		if(subredditDto==null) {
			return null;
		}
		SubredditBuilder subreddit=Subreddit.builder();
		subreddit.id(subredditDto.getId());
		subreddit.name(subredditDto.getName());
		subreddit.description(subredditDto.getDescription());
		return subreddit.build();
		
	}

}
