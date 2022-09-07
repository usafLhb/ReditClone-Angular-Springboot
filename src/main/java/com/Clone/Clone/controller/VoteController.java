package com.Clone.Clone.controller;

import com.Clone.Clone.dto.VoteDto;
import com.Clone.Clone.exception.PostNotFoundException;
import com.Clone.Clone.exception.SpringRedditException;
import com.Clone.Clone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes/")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class VoteController {
private final VoteService voteService;

@PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) throws PostNotFoundException, SpringRedditException {
    voteService.vote(voteDto);
    return new ResponseEntity<>(HttpStatus.OK);
    }
}
