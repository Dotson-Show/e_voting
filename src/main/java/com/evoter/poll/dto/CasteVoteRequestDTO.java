package com.evoter.poll.dto;

import lombok.Data;

@Data
public class CasteVoteRequestDTO {

    private Long candidateId;
    private Long pollId;

}
