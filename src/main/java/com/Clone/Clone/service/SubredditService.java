package com.Clone.Clone.service;

import com.Clone.Clone.dto.SubredditDto;
import com.Clone.Clone.exception.SpringRedditException;
import com.Clone.Clone.mapper.SubredditMapper;
import com.Clone.Clone.model.Subreddit;
import com.Clone.Clone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class SubredditService {

    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final AuthService authService;
    @Transactional
    public SubredditDto save(SubredditDto subredditDto){
        Subreddit subreddit= subredditRepository.save(mapSubredditDto(subredditDto));
        subredditDto.setId(subreddit.getId());
        
        return subredditDto;
    }
    private Subreddit mapSubredditDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .user(authService.getCurrentUser())
                .createdDate(java.time.Instant.now())
                .build();
    }
    @Transactional
    public List<SubredditDto> getAll(){
      return  subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPosts(String.valueOf(subreddit.getPosts().size()))
                .build();
    }
    public SubredditDto getSubreddit(Long id) throws SpringRedditException {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
        return subredditMapper.mapSubredditToDto(subreddit);
    }
}
