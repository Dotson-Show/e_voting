package com.evoter.candidate.service;


import com.evoter.candidate.dto.CreateUpdateCandidateRequestDto;
import com.evoter.candidate.model.Candidate;
import com.evoter.candidate.repository.CandidateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService{

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate createCandidate(CreateUpdateCandidateRequestDto requestDto) {
        log.info("Request to create a candidate with payload={}", requestDto);

        // validate that candidate details are not null

        // check if candidate already exist

        //save
        Candidate candidate = new Candidate();
        candidate.setName(requestDto.getName());
        candidate.setAge(requestDto.getAge());
        candidate.setSex(requestDto.getSex());
        candidate.setPartyId(requestDto.getPartyId());
        candidate.setPollTypeId(requestDto.getPollTypeId());

        return candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return null;
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return null;
    }

    @Override
    public void deleteCandidateById(Long id) {

    }

    @Override
    public Candidate findByName(String name) {
        return candidateRepository.findByName(name);
    }
}
