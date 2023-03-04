package com.evoter.vote.dto;


import lombok.Data;

@Data
public class VoteDto {

    private Long candidateId;

    private String partyName;

    private Long pollId;
}
