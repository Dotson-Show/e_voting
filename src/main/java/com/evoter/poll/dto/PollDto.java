package com.evoter.poll.dto;

import com.evoter.candidate.model.Candidate;

import java.util.Set;

public class PollDto {

    private Long pollTypeId;

    private Set<Candidate> candidateList;
}
