package com.evoter.candidate.controller;

import com.evoter.candidate.dto.CreateUpdateCandidateRequestDto;
import com.evoter.candidate.model.Candidate;
import com.evoter.candidate.service.CandidateService;
import com.evoter.general.dto.Response;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author showunmioludotun
 */
@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {
    private final CandidateService candidateService;

    private final GeneralService generalService;

    @Autowired
    public CandidateController(CandidateService candidateService, GeneralService generalService) {
        this.candidateService = candidateService;
        this.generalService = generalService;
    }

    @PostMapping("/create")
    public Response addCandidate(@RequestBody CreateUpdateCandidateRequestDto request) {

        Candidate data = candidateService.createCandidate(request);
        return generalService.prepareResponse(ResponseCodeAndMessage.SUCCESSFUL_0, data);

    }

    @GetMapping("/candidates")
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        try {
            List<Candidate> candidates = candidateService.getAllCandidates();
            if (candidates.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(candidates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/candidates/{candidateId}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable("candidateId") Long id) {
        try {
            Candidate candidate = candidateService.getCandidateById(id);
            if (candidate == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/candidates/{candidateId}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable("candidateId") Long id) {
        try {
            candidateService.deleteCandidateById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
