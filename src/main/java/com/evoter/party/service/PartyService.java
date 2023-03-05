package com.evoter.party.service;

import com.evoter.party.dto.CreateUpdatePartyDTO;
import com.evoter.party.model.Party;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author showunmioludotun
 */
@Service
public interface PartyService {

    public Party createParty(CreateUpdatePartyDTO request);
    public List<Party> getAllParties();
    public Party getPartyById(Long id);
    public void deletePartyById(Long id);
}
