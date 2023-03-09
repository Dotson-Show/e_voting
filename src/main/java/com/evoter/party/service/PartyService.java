package com.evoter.party.service;

import com.evoter.party.dto.request.PartyRequestDto;
import com.evoter.party.dto.response.PartyResponseDto;
import com.evoter.party.model.Party;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */
@Service
public interface PartyService {

    PartyResponseDto createParty(PartyRequestDto request);

    PartyResponseDto updateParty(Long PartyId, PartyRequestDto request);

    List<Party> getAllParties();

    Party getPartyById(Long id);

    void deletePartyById(Long id);
}
