package com.hn004.reddit.Service;

import java.util.List;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hn004.reddit.Entity.Subreddit;
import com.hn004.reddit.Repository.SubredditRepo;
import com.hn004.reddit.dto.SubredditDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {
	
	private final SubredditRepo subredditRepo;
	int postCount=0;
	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit=subredditRepo.save(mapSubredditDto(subredditDto));
//		subredditRepo.save(subreddit);
		subredditDto.setId(subreddit.getId());
		
		subredditDto.setNumberofPosts(postCount);
		return subredditDto;
	}

	private Subreddit mapSubredditDto(SubredditDto subredditDto) {
		postCount++;
		return Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription())
		.build();
	}

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<SubredditDto> getAll() {
		
		return subredditRepo.findAll().stream()
				.map(this::mapToDto)
				.collect
				(toList());
	}
	
	 private SubredditDto mapToDto(Subreddit subreddit) {
	        return SubredditDto.builder().name(subreddit.getName())
	                .description(subreddit.getDescription())
	                .build();
	    }
//	
//	private Subreddit mapToDto(SubredditDto subredditDto) {
//		return Subreddit.builder().name(subredditDto.getSubredditName())
//				.description(subredditDto.getDescription()).build();
//	}

}
