package com.hn004.reddit.dto;

import com.hn004.reddit.Entity.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VoteDto {
	
	private  VoteType voteType;
	private Long postId;
	

}
