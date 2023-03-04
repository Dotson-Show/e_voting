package com.evoter.vote.controller;

import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.vote.dto.VoteDto;
import com.evoter.vote.model.Vote;
import com.evoter.poll.dto.CasteVoteRequestDTO;
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
@RequestMapping("/api/v1")
public class VoteController {
    private final VoteService voteService;

    private final GeneralService generalService;

    @Autowired
    public VoteController(VoteService voteService, GeneralService generalService) {
        this.voteService = voteService;
        this.generalService = generalService;
    }

//    @PostMapping("/votes")
//    public ResponseEntity<VoteDto> casteVote(@RequestBody CasteVoteRequestDTO request) {
//        try {
//            VoteDto savedVote = voteService.casteVote(request);
//            if (savedVote == null) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//            return new ResponseEntity<>(savedVote, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/casteVote")
    public Response casteVote(@RequestBody CasteVoteRequestDTO requestDTO) {

        VoteDto data = voteService.casteVote(requestDTO);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping("/votes")
    public ResponseEntity<List<Vote>> getAllVotes() {
        try {
            List<Vote> votes = voteService.getAllVotes();
            if (votes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(votes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/votes/{voteId}")
    public ResponseEntity<Vote> getVoteById(@PathVariable("voteId") Long id) {
        try {
            Vote vote = voteService.getVoteById(id);
            if (vote == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(vote, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
