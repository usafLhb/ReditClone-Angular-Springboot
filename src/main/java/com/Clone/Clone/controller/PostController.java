package com.Clone.Clone.controller;

import com.Clone.Clone.dto.PostRequest;
import com.Clone.Clone.dto.PostResponse;
import com.Clone.Clone.exception.SubredditNotFoundException;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@CrossOrigin(origins="*")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) throws SubredditNotFoundException {
        System.out.println("postRequest "+postRequest);
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).
                body(postService.getAll());
    }



    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) throws SubredditNotFoundException {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit (@PathVariable Long id) throws SubredditNotFoundException {
        return  status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
    }


    @GetMapping("by-user/{user}")
    public ResponseEntity<List<PostResponse>> getPostsByUser (@PathVariable String user) throws SubredditNotFoundException {
        System.out.println("user est "+user);
        return status(HttpStatus.OK).body(postService.getPostsByUsername(user));
    }



}
