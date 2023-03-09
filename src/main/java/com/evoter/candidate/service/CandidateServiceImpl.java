package com.evoter.candidate.service;


import com.evoter.candidate.dto.CreateUpdateCandidateRequestDto;
import com.evoter.candidate.model.Candidate;
import com.evoter.candidate.repository.CandidateRepository;
import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CandidateServiceImpl implements CandidateService{

    private final CandidateRepository candidateRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Candidate createCandidate(CreateUpdateCandidateRequestDto requestDto) {
        log.info("Request to create a candidate with payload={}", requestDto);

        // validate that candidate details are not null
        if(Objects.isNull(requestDto)) throw new GeneralException(ResponseCodeAndMessage.INVALID_JSON_REQUEST_DATA_90
                .responseCode,"request cannot be null");


        // check if candidate already exist

        if(candidateRepository.isExistsByEmail(requestDto.getEmail())) throw new GeneralException(ResponseCodeAndMessage
                .ALREADY_EXIST_86.responseCode,"Candidates already exists");

        Candidate candidate = new Candidate();
        candidate.setName(requestDto.getName());
        candidate.setAge(requestDto.getAge());
        candidate.setSex(requestDto.getSex());
        candidate.setPartyId(requestDto.getPartyId());
        candidate.setPollTypeId(requestDto.getPollTypeId());
        candidate.setEmail(requestDto.getEmail());

        //save
        return candidateRepository.save(candidate);
    }


    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getCandidateById(Long id) {
        if(Objects.isNull(id))throw new GeneralException(ResponseCodeAndMessage.INVALID_JSON_REQUEST_DATA_90.
                responseCode,"id cannot be null");
        return candidateRepository.findById(id).orElseThrow(()->new GeneralException(ResponseCodeAndMessage.
                RECORD_NOT_FOUND_88.responseCode,"Candidate not found"));
    }

    @Override
    public void deleteCandidateById(Long id) {
        if(Objects.isNull(id))throw new GeneralException(ResponseCodeAndMessage.INVALID_JSON_REQUEST_DATA_90
                .responseCode,"id cannot be null");
        candidateRepository.deleteById(id);
    }

    @Override
    public Candidate findByName(String name) {
        return candidateRepository.findByName(name);
    }
}
