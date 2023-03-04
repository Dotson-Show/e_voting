package com.evoter.poll.service;

import com.evoter.poll.dto.AddPollRequest;
import com.evoter.poll.dto.PollDto;
import com.evoter.poll.model.Poll;
import com.evoter.poll.repository.PollRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */

public interface PollService {

    PollDto createPoll(AddPollRequest requestDTO);

    public List<Poll> getAllPolls();

     Poll getPollById(Long id);

     void deletePollById(Long id);
}
