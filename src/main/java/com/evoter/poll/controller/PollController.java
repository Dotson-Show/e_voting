package com.evoter.poll.controller;

import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.poll.dto.AddPollRequest;
import com.evoter.poll.dto.PollDto;
import com.evoter.poll.model.Poll;
import com.evoter.poll.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author showunmioludotun
 */
@RestController
@RequestMapping("/api/v1/polls")
public class PollController {
    private final PollService pollService;

    private final GeneralService generalService;

    @Autowired
    public PollController(PollService pollService, GeneralService generalService) {
        this.pollService = pollService;
        this.generalService = generalService;
    }

    @PostMapping("/create")
    public Response createPoll(@RequestBody AddPollRequest requestDTO) {

        PollDto data = pollService.createPoll(requestDTO);

        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);
    }

    @GetMapping("/polls")
    public ResponseEntity<List<Poll>> getAllPolls() {
        try {
            List<Poll> polls = pollService.getAllPolls();
            if (polls.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(polls, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<Poll> getPollById(@PathVariable("pollId") Long id) {
        try {
            Poll poll = pollService.getPollById(id);
            if (poll == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(poll, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<Void> deletePollById(@PathVariable("pollId") Long id) {
        try {
            pollService.deletePollById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
