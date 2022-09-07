package com.Clone.Clone.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subreddit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Nullable
    private String name;
    @Nullable
    private String  description;
    @OneToMany(fetch=LAZY)
    private List<Post>posts;
    private Instant createdDate;
    @ManyToOne(fetch=LAZY)
    private User user;

}
