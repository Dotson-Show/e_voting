package com.evoter.candidate.repository;

import com.evoter.candidate.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author showunmioludotun
 */
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    boolean existsById(Long id);
}
