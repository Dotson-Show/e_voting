package com.evoter.vote.repository;

import com.evoter.vote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author showunmioludotun
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

    List<Vote> findByPollId(Long id);


}
