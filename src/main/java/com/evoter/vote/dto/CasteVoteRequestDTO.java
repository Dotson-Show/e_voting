package com.evoter.vote.dto;

import lombok.Data;

@Data
public class CasteVoteRequestDTO {

    private String partyName;
    private Long candidateId;
    private Long pollId;
    private String govtId;

}
