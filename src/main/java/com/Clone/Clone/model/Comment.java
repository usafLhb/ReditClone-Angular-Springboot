package com.Clone.Clone.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotNull
    private String text;
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="postId",referencedColumnName = "postId")
    private Post post;
    @ManyToOne(fetch=LAZY)
    @JoinColumn(name="userId",referencedColumnName = "userId")
    private User user;

}
