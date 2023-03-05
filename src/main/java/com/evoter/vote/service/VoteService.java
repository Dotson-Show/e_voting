package com.evoter.vote.service;

import com.evoter.vote.dto.AllVoteStatisticsDTO;
import com.evoter.vote.dto.CasteVoteRequestDTO;
import com.evoter.vote.dto.Day;
import com.evoter.vote.dto.VoteDto;
import com.evoter.vote.model.Vote;

import java.util.List;

/**
 * @author showunmioludotun
 */

public interface VoteService {

    Vote casteVote(CasteVoteRequestDTO request);

    List<Vote> getAllVotes();

    Vote getVoteById(Long id);

    AllVoteStatisticsDTO getAllVoteStatistics(Long pollId);
}
