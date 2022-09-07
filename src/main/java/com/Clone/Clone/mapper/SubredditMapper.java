package com.Clone.Clone.mapper;



import com.Clone.Clone.dto.SubredditDto;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "created_date", expression = "java((subreddit.getCreatedDate()))")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default String mapPosts(List<Post> numberOfPosts) {
        return String.valueOf(numberOfPosts.size());
    }

}
