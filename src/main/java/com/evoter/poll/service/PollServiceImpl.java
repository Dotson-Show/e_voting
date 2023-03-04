package com.evoter.poll.service;

import com.evoter.candidate.model.Candidate;
import com.evoter.candidate.service.CandidateService;
import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.permission.model.Permission;
import com.evoter.poll.dto.AddPollRequest;
import com.evoter.poll.dto.PollDto;
import com.evoter.poll.model.Poll;
import com.evoter.poll.repository.PollRepository;
import com.evoter.pollType.repository.PollTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PollServiceImpl implements PollService{

    private final PollRepository pollRepository;

    private final GeneralService generalService;

    private final PollTypeRepository pollTypeRepository;

    private final CandidateService candidateService;

    public PollServiceImpl(PollRepository pollRepository, GeneralService generalService, PollTypeRepository pollTypeRepository, CandidateService candidateService) {
        this.pollRepository = pollRepository;
        this.generalService = generalService;
        this.pollTypeRepository = pollTypeRepository;
        this.candidateService = candidateService;
    }

    @Override
    public PollDto createPoll(AddPollRequest requestDTO) {
        log.info("Request to create poll with payload={}", requestDTO);

        //validate that pollType exist
        if (!pollTypeRepository.existsById(requestDTO.getPollTypeId())) {
            throw new GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode, "Poll Type not found");
        }

        //permission
        List<Candidate> candidateList = requestDTO.getCandidateNames().stream().map(candidateService::findByName).collect(Collectors.toList());

        // save role
        Poll poll = new Poll();
        poll.setPollName(requestDTO.getPollName());
        poll.setPollTypeId(requestDTO.getPollTypeId());
        poll.setcandidateList(candidateList);

        pollRepository.save(poll);

        return getPollDTO(poll);
    }

    @Override
    public List<Poll> getAllPolls() {
        return null;
    }

    @Override
    public Poll getPollById(Long id) {
        return null;
    }

    @Override
    public void deletePollById(Long id) {

    }


    public PollDto getPollDTO(Poll poll) {

        PollDto voteDto = new PollDto();
        generalService.createDTOFromModel(voteDto, poll);

        return voteDto;
    }
}
