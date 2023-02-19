package com.hn004.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
	
	private Long id;
	private String subredditName;
	private String description;
	private Integer numberofPosts;

}
