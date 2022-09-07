package com.Clone.Clone.dto;

import com.Clone.Clone.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
