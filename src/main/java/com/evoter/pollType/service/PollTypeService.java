package com.evoter.pollType.service;

import com.evoter.pollType.dto.AddPollTypeRequest;
import com.evoter.pollType.dto.PollTypeDTO;
import com.evoter.pollType.model.PollType;

import java.util.List;

/**
 * @author showunmioludotun
 */

public interface PollTypeService {

    PollTypeDTO createPollType(AddPollTypeRequest requestDTO);

    List<PollType> getAllPollTypes();

    public PollType getPollTypeById(Long id);

    public void deletePollTypeById(Long id);

}
