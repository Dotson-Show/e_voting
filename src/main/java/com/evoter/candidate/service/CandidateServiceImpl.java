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
        if(requestDto ==null) throw new GeneralException(ResponseCodeAndMessage.INVALID_JSON_REQUEST_DATA_90
                .responseCode,"request cannot be null");

        // check if candidate already exist
        Candidate candidate = new Candidate();
        candidate.setName(requestDto.getName());
        candidate.setAge(requestDto.getAge());
        candidate.setSex(requestDto.getSex());
        candidate.setPartyId(requestDto.getPartyId());
        candidate.setPollTypeId(requestDto.getPollTypeId());
        if(candidateExists(candidate)) throw new GeneralException(ResponseCodeAndMessage.ALREADY_EXIST_86.
                responseCode,"Candidates already exists");

        //save
        return candidateRepository.save(candidate);
    }

    public boolean candidateExists(Candidate candidate) {
        log.info("Checks if candidate exists in the database{}",candidate);
        String query = "SELECT c FROM Candidate c WHERE c.name = :name AND c.sex = :sex AND c.partyId = " +
                ":partyId AND c.age = :age AND c.pollTypeId = :pollTypeId";
        TypedQuery<Candidate> typedQuery = entityManager.createQuery(query, Candidate.class)
                .setParameter("name", candidate.getName())
                .setParameter("sex", candidate.getSex())
                .setParameter("partyId", candidate.getPartyId())
                .setParameter("age",candidate.getAge())
                .setParameter("pollTypeId",candidate.getPollTypeId());

        List<Candidate> candidates = typedQuery.getResultList();

        return !candidates.isEmpty();
    }
    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElseThrow(()->new GeneralException(ResponseCodeAndMessage.
                RECORD_NOT_FOUND_88.responseCode,"Candidate not found"));
    }

    @Override
    public void deleteCandidateById(Long id) {
         candidateRepository.deleteById(id);
    }

    @Override
    public Candidate findByName(String name) {
        return candidateRepository.findByName(name);
    }
}
