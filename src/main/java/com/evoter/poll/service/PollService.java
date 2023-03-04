package com.evoter.poll.service;

import com.evoter.poll.dto.AddPollRequest;
import com.evoter.poll.model.Poll;

import java.util.List;

/**
 * @author showunmioludotun
 */

public interface PollService {

    Poll createPoll(AddPollRequest requestDTO);

    public List<Poll> getAllPolls();

    Poll getPollById(Long id);

    void deletePollById(Long id);
}
