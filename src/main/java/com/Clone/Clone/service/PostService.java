package com.Clone.Clone.service;

import com.Clone.Clone.dto.PostRequest;
import com.Clone.Clone.dto.PostResponse;
import com.Clone.Clone.exception.SubredditNotFoundException;
import com.Clone.Clone.mapper.PostMapper;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.Subreddit;
import com.Clone.Clone.model.User;
import com.Clone.Clone.repository.PostRepository;
import com.Clone.Clone.repository.SubredditRepository;
import com.Clone.Clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    public void save(PostRequest postRequest) {


        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Error  dans  cette ligne "+postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
    }

    @Transactional
    public PostResponse getPost(Long id) throws SubredditNotFoundException {
        Post post=postRepository.findById(id).orElseThrow(()->new SubredditNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }
    @Transactional
    public List<PostResponse>  getAll(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

//    @Transactional
//    public List<PostResponse> getPostsByUsername(String userName) throws SubredditNotFoundException {
//        User user=userRepository.findByUsername(userName).orElseThrow(()->new UsernameNotFoundException(userName));
//        System.out.println("user est  "+user.toString());
//        return (List<PostResponse>) postRepository.findByUser(user).stream().map(postMapper:: mapToDto).collect(toList());
//    }
@Transactional
public List<PostResponse> getPostsByUsername(String username) {
    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
    return postRepository.findByUser(user)
            .stream()
            .map(postMapper::mapToDto)
            .collect(toList());
}
    @Transactional
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }



}
