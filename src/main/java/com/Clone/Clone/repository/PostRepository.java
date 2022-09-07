package com.Clone.Clone.repository;

import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.Subreddit;
import com.Clone.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUser (User user);
    List<Post>  findAllBySubreddit (Subreddit subreddit);
}
