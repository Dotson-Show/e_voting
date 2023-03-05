package com.evoter.vote.dto;

import lombok.Data;

@Data
public class CasteVoteRequestDTO {

    private Long candidateId;
    private Long pollId;

}
