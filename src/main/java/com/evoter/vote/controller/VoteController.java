package com.evoter.vote.controller;

import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.vote.dto.AllVoteStatisticsDTO;
import com.evoter.vote.dto.CasteVoteRequestDTO;
import com.evoter.vote.model.Vote;
import com.evoter.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author showunmioludotun
 */
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {
    private final VoteService voteService;

    private final GeneralService generalService;

    @Autowired
    public VoteController(VoteService voteService, GeneralService generalService) {
        this.voteService = voteService;
        this.generalService = generalService;
    }

    @PostMapping("/casteVote")
    public Response casteVote(@RequestBody CasteVoteRequestDTO requestDTO) {

        Vote data = voteService.casteVote(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping("")
    public Response getAllVotes() {

        List<Vote> data = voteService.getAllVotes();

        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping("/result/{pollId}")
    public Response getResult(@PathVariable Long pollId) {

        AllVoteStatisticsDTO data = voteService.getAllVoteStatistics(pollId);

        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping("/votes/{voteId}")
    public ResponseEntity<Vote> getVoteById(@PathVariable("voteId") Long id) {
        try {
            Vote vote = voteService.getVoteById(id);
            if (vote == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vote, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
