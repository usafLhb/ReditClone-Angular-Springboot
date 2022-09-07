package com.Clone.Clone.service;

import com.Clone.Clone.dto.CommentRequest;
import com.Clone.Clone.exception.CommnetNotFoundException;
import com.Clone.Clone.exception.PostNotFoundException;
import com.Clone.Clone.mapper.CommnetMapper;
import com.Clone.Clone.model.Comment;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.User;
import com.Clone.Clone.repository.CommentRepository;
import com.Clone.Clone.repository.PostRepository;
import com.Clone.Clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private  final PostRepository postRepository;
    private AuthService authService;
    private CommnetMapper commnetMapper;
    private  final UserRepository userRepository;

    public void save(CommentRequest commentRequest) throws PostNotFoundException {
        System.out.println("commentRequest "+commentRequest.toString());
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId().toString()+" Post not found !"));
        System.out.println("post "+post.toString());
        commentRepository.save(commnetMapper.map( commentRequest, post, authService.getCurrentUser()));
    }
    @Transactional
    public List<CommentRequest> getAll(){
        return commentRepository.findAll()
                .stream()
                .map(commnetMapper::mapToDto)
                .collect(toList());
    }


    public List<CommentRequest> getAllCommentsForPost(Long postId) throws PostNotFoundException {
        System.out.println("casaa");
        Post post = postRepository.findById(3L)
                .orElseThrow(() -> new PostNotFoundException("Sorry  inexistant "+postId.toString()));
        System.out.println("ost waooo "+post.toString());
        return commentRepository.findByPost(post)
                .stream()
                .map(commnetMapper::mapToDto).collect(toList());
    }
    public List<CommentRequest> getAllCommentsByIdPost(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Sorry  inexistant "+id.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commnetMapper::mapToDto).collect(toList());
    }
    public List<CommentRequest> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

                    return commentRepository.findAllByUser(user)
                .stream()
                .map(commnetMapper::mapToDto)
                .collect(toList());
    }



}
