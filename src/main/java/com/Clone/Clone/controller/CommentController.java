package com.Clone.Clone.controller;

import com.Clone.Clone.dto.CommentRequest;
import com.Clone.Clone.exception.CommnetNotFoundException;
import com.Clone.Clone.exception.PostNotFoundException;
import com.Clone.Clone.exception.SubredditNotFoundException;
import com.Clone.Clone.model.Comment;
import com.Clone.Clone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comment")
@CrossOrigin(origins="*")

public class CommentController {
    private final CommentService commentService;



    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentRequest commentsDto) throws PostNotFoundException {
        commentService.save(commentsDto);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping
    public List<CommentRequest> getAll(){
        System.out.println("Waoo comment");
        return  commentService.getAll();
    }
    @GetMapping("/postId/{id}")
    public List<CommentRequest> getAllCommentsForPost(@PathVariable Long postId) throws PostNotFoundException {
        System.out.println("mouchhh");
        return  null;
    }
    @GetMapping("/Idpost/{id}")
    public List<CommentRequest> getAllCommentsByIdPost(@PathVariable Long id) throws PostNotFoundException {
        return  commentService.getAllCommentsByIdPost(id);
    }

    @GetMapping("/userName/{userName}")
    public List<CommentRequest> getAllCommentsForUser(@PathVariable String userName) throws PostNotFoundException, CommnetNotFoundException {
        return  commentService.getAllCommentsForUser(userName);
    }


}
