package com.hn004.reddit.Service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.Entity.Subreddit.SubredditBuilder;
import com.hn004.reddit.Repository.SubredditRepo;
import com.hn004.reddit.Security.JwtAuthenticationFilter;
import com.hn004.reddit.Service.CustomException.StringRedditException;
import com.hn004.reddit.dto.SubredditDto;
import com.hn004.reddit.dto.SubredditDto.SubredditDtoBuilder;
import com.hn004.reddit.mapper.SubredditMapper;
//import com.hn004.reddit.mapper.SubredditMapperImpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	
	private final SubredditRepo subredditRepo;
	
	SubredditMapper subredditMapper;
	
	
	static Integer postCount=0;
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit=subredditRepo.save(subredditMapper.mapDtoToSubreddit(subredditDto));
//		subredditRepo.save(subreddit);
		subredditDto.setId(subreddit.getId());
		
		subredditDto.setNumberofPosts(postCount++);
		return subredditDto;
	}



	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		
		return subredditRepo.findAll().stream()
				.map(subredditMapper::mapSubredditToDto)
				.collect
				(toList());
	}



	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit=subredditRepo.findById(id)
				.orElseThrow(() -> new StringRedditException("no subreddit found"));
		
		return subredditMapper.mapSubredditToDto(subreddit);
	}
	


}
