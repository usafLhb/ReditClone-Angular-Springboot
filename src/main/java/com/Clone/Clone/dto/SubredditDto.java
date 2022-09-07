package com.Clone.Clone.dto;

import com.Clone.Clone.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
    private Long id;
    private String name;
    private  String description;
    private String numberOfPosts;
    private Instant created_date;
    private String username;
}
