package com.evoter.party.service;

import com.evoter.party.dto.UpdatePartyRequest;
import com.evoter.party.model.Party;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */
@Service
public interface PartyService {

    Party createParty(UpdatePartyRequest request);

    Party updateParty(Long PartyId, UpdatePartyRequest request);

    List<Party> getAllParties();

    Party getPartyById(Long id);

    void deletePartyById(Long id);
}
