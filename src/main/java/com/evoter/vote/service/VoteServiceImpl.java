package com.evoter.vote.service;

import com.evoter.candidate.repository.CandidateRepository;
import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.poll.repository.PollRepository;
import com.evoter.user.repository.UserRepository;
import com.evoter.vote.dto.AllVoteStatisticsDTO;
import com.evoter.vote.dto.CasteVoteRequestDTO;
import com.evoter.vote.model.Vote;
import com.evoter.vote.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class VoteServiceImpl implements VoteService {

    public static final String LP_VOTE_COUNT = "LP Vote Count";
    public static final String APC_VOTE_COUNT = "APC Vote Count";
    private final VoteRepository voteRepository;

    private final GeneralService generalService;

    private final PollRepository pollRepository;

    private final CandidateRepository candidateRepository;

    private final UserRepository userRepository;

    public VoteServiceImpl(VoteRepository voteRepository, GeneralService generalService, PollRepository pollRepository, CandidateRepository candidateRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.generalService = generalService;
        this.pollRepository = pollRepository;
        this.candidateRepository = candidateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Vote casteVote(CasteVoteRequestDTO request) {
        log.info("Request to Caste vote with payload = {}", request);

        //check that Poll exist
        if (!pollRepository.existsById(request.getPollId())) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Poll not found");
        }

        //check that candidate is valid
        if (!candidateRepository.existsById(request.getPollId())) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Candidate not found");
        }

        // validate Govt Id
        if (!userRepository.existsByGovtId(request.getGovtId())) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Voter's Govt ID is invalid");
        }

        Vote vote = new Vote();
        vote.setPollId(request.getPollId());
        vote.setCandidateId(request.getCandidateId());
        vote.setPartyName(request.getPartyName());
//        vote.setVoterEmail("ehisjude420@gmail.com");
        vote.setGovtId(request.getGovtId());

        return voteRepository.save(vote);
    }

    @Override
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public Vote getVoteById(Long id) {


        //
        return null;
    }

    @Override
    public AllVoteStatisticsDTO getAllVoteStatistics(Long pollId) {
        log.info("Request to get Vote statistics for Poll Id ={}", pollId);

        List<Vote> voteList = voteRepository.findByPollId(pollId);
        log.info("voteList ={}", voteList);

        AllVoteStatisticsDTO voteStatisticsDTO = new AllVoteStatisticsDTO();

        if (!voteList.isEmpty()) {

            Map<String, Integer> voteBreakDown = getVoteCountAndPartyName(voteList);

            voteStatisticsDTO.setTotalVoteCountForAPC(voteBreakDown.get(APC_VOTE_COUNT));
            voteStatisticsDTO.setTotalVoteCountForLP(voteBreakDown.get(LP_VOTE_COUNT));

            voteStatisticsDTO.setVoteList(voteList);
        }

        return voteStatisticsDTO;

    }

    private Map<String, Integer> getVoteCountAndPartyName(List<Vote> voteList) {

        int totalVoteCountForAPC = 0;
        int totalVoteCountForLP = 0;


        Map<String, Integer> voteCountAndPartyName = new HashMap<>();

        for (Vote vote : voteList) {

            if (vote.getPartyName().equals("APC")) {
                totalVoteCountForAPC++;

            } else {
                totalVoteCountForLP++;
            }

        }

        voteCountAndPartyName.put(LP_VOTE_COUNT, totalVoteCountForLP);
        voteCountAndPartyName.put(APC_VOTE_COUNT, totalVoteCountForAPC);

        return voteCountAndPartyName;
    }


}
