package com.Clone.Clone.repository;

import com.Clone.Clone.model.Subreddit;
import com.Clone.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit,Long> {

    Optional<Subreddit> findByName (String username);
}
