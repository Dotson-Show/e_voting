package com.evoter.pollType.service;

import com.evoter.pollType.dto.AddPollTypeRequest;
import com.evoter.pollType.model.PollType;
import com.evoter.pollType.repository.PollTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */
@Service
public class PollTypeService {
    private final PollTypeRepository pollTypeRepository;

    public PollTypeService(PollTypeRepository pollTypeRepository) {
        this.pollTypeRepository = pollTypeRepository;
    }

    public PollType addPollType(AddPollTypeRequest request) {
        PollType pollType = new PollType();
//        pollType.setName(request.name());
        return pollTypeRepository.save(pollType);
    }

    public List<PollType> getAllPollTypes() {
        return pollTypeRepository.findAll();
    }

    public PollType getPollTypeById(Long id) {
        return pollTypeRepository.findById(id).orElse(null);
    }

    public void deletePollTypeById(Long id) {
        pollTypeRepository.deleteById(id);
    }
}
