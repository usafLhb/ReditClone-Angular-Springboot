package com.Clone.Clone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PostRequest {
    private  Long postId;
    private String subredditName;
    private  String postName;
    private String url;
    private String description;
}
