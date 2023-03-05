package com.evoter.vote.dto;


import com.evoter.vote.model.Vote;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AllVoteStatisticsDTO extends VoteStatisticsDTO {

    //    private List<Map<String, Object>> voteList = new ArrayList<>();
    private List<Vote> voteList = new ArrayList<>();
}