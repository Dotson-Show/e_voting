package com.evoter.vote.service;

import com.evoter.candidate.repository.CandidateRepository;
import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.poll.dto.CasteVoteRequestDTO;
import com.evoter.poll.repository.PollRepository;
import com.evoter.vote.dto.VoteDto;
import com.evoter.vote.model.Vote;
import com.evoter.vote.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final GeneralService generalService;

    private final PollRepository pollRepository;

    private final CandidateRepository candidateRepository;

    public VoteServiceImpl(VoteRepository voteRepository, GeneralService generalService, PollRepository pollRepository, CandidateRepository candidateRepository) {
        this.voteRepository = voteRepository;
        this.generalService = generalService;
        this.pollRepository = pollRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public VoteDto casteVote(CasteVoteRequestDTO request) {
        log.info("Request to Caste vote with payload = {}", request);

        //check that Poll exist
        if (!pollRepository.existsById(request.getPollId())) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Poll not found");
        }

        //check that candidate is valid
        if (!candidateRepository.existsById(request.getPollId())) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Candidate not found");
        }

        Vote vote = new Vote();
        vote.setPollId(request.getPollId());
        vote.setCandidateId(request.getCandidateId());

        voteRepository.save(vote);

        return getVoteDTO(vote);
    }

    @Override
    public List<Vote> getAllVotes() {
        return null;
    }

    @Override
    public Vote getVoteById(Long id) {
        return null;
    }

    public VoteDto getVoteDTO(Vote vote) {

        VoteDto voteDto = new VoteDto();
        generalService.createDTOFromModel(voteDto, vote);

        return voteDto;
    }

}
