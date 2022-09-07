package com.Clone.Clone.repository;

import com.Clone.Clone.model.Comment;
import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);
}
