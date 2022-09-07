package com.Clone.Clone.mapper;


import com.Clone.Clone.dto.PostRequest;
import com.Clone.Clone.dto.PostResponse;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.Subreddit;
import com.Clone.Clone.model.User;
import com.Clone.Clone.model.Vote;
import com.Clone.Clone.repository.CommentRepository;
import com.Clone.Clone.repository.VoteRepository;
import com.Clone.Clone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
 
@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository ;
    @Autowired
    private VoteRepository voteRepository ;
    @Autowired
    private AuthService authService ;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
//    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);



    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

//    String getDuration(Post post) {
//        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
//    }



    }
