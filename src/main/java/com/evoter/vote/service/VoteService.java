package com.evoter.vote.service;

import com.evoter.vote.dto.AddVoteRequest;
import com.evoter.vote.model.Vote;
import com.evoter.vote.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Vote addVote(AddVoteRequest request) {
        Vote vote = new Vote();
//        vote.setUserId(request.userId());
//        vote.setCandidateId(request.candidateId());
//        vote.setPollId(request.pollId());
        return voteRepository.save(vote);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    public Vote getVoteById(Long id) {
        return voteRepository.findById(id).orElse(null);
    }
}
