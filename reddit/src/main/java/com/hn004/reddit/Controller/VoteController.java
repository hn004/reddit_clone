package com.hn004.reddit.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hn004.reddit.Service.VoteService;
import com.hn004.reddit.dto.VoteDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class VoteController {
	
	private final VoteService voteService;
	
	public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto){
		voteService.vote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK); 
	}

}
