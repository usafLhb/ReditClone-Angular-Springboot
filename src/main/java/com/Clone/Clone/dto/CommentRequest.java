package com.Clone.Clone.dto;

import com.Clone.Clone.model.Post;
import com.Clone.Clone.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {
    private Long id;
    private Long postId;
    private Instant createdDate;
    private String text;
    private String userName;


}
