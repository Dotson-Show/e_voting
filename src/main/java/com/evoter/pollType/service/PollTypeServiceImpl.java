package com.evoter.pollType.service;

import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.general.service.GeneralService;
import com.evoter.pollType.dto.AddPollTypeRequest;
import com.evoter.pollType.dto.PollTypeDTO;
import com.evoter.pollType.model.PollType;
import com.evoter.pollType.repository.PollTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class PollTypeServiceImpl implements PollTypeService {

    private final PollTypeRepository pollTypeRepository;

    private final GeneralService generalService;

    public PollTypeServiceImpl(PollTypeRepository pollTypeRepository, GeneralService generalService) {
        this.pollTypeRepository = pollTypeRepository;
        this.generalService = generalService;
    }

    @Override
    public PollType createPollType(AddPollTypeRequest requestDTO) {
        log.info("Request to create a poll Type with payload={}", requestDTO);

        // validate pollType name
        if (pollTypeRepository.existsByName(requestDTO.getName())) {
            throw new GeneralException(ResponseCodeAndMessage.ALREADY_EXIST_86.responseCode, "Poll Type already exist");
        }

        PollType pollType = new PollType();
        pollType.setName(requestDTO.getName());

        return pollTypeRepository.save(pollType);

    }

    @Override
    public List<PollType> getAllPollTypes() {
        return null;
    }

    @Override
    public PollType getPollTypeById(Long id) {
        return null;
    }

    @Override
    public void deletePollTypeById(Long id) {

    }

    public PollTypeDTO getPollTypeDTO(PollType pollType) {

        PollTypeDTO pollTypeDTO = new PollTypeDTO();
        generalService.createDTOFromModel(pollTypeDTO, pollType);

        return pollTypeDTO;
    }
}
