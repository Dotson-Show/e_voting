package com.evoter.vote.dto;

import lombok.Data;

@Data
public class AddVoteRequest {

    private Long userId;
    private Integer candidateId;
    private Integer pollId;

}
