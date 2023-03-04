package com.evoter.poll.repository;

import com.evoter.poll.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author showunmioludotun
 */
public interface PollRepository extends JpaRepository<Poll, Long> {


    boolean existsById(Long id);

    Poll findByPollName(String pollName);

}
