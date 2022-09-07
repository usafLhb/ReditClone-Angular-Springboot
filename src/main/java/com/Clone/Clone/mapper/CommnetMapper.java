package com.Clone.Clone.mapper;

import com.Clone.Clone.dto.CommentRequest;
import com.Clone.Clone.dto.PostResponse;
import com.Clone.Clone.model.Comment;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommnetMapper {

//    @Mapping(target = "post", source = "post")
//    @Mapping(target = "user", source = "user")
//    @Mapping(target = "createdDate", source = "java(java.time.Instant.now())")
//    Comment map(CommentRequest commentRequest, Post post, User user);
//
//
//    Comment map(PostRequest postRequest, Post post, User user);




    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentRequest commentsDto, Post post, User user);


    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentRequest mapToDto(Comment comment);
}
