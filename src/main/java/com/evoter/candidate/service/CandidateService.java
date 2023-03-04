package com.evoter.candidate.service;

import com.evoter.candidate.dto.CreateUpdateCandidateRequestDto;
import com.evoter.candidate.model.Candidate;
import com.evoter.candidate.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */

public interface CandidateService {


    Candidate createCandidate(CreateUpdateCandidateRequestDto requestDto);
     List<Candidate> getAllCandidates();
    Candidate getCandidateById(Long id);
    void deleteCandidateById(Long id) ;
}
