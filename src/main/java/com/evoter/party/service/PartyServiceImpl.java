package com.evoter.party.service;

import com.evoter.party.dto.CreateUpdatePartyDTO;
import com.evoter.party.model.Party;
import com.evoter.party.repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PartyServiceImpl implements PartyService{

    private final PartyRepository partyRepository;

    public PartyServiceImpl(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }


    @Override
    public Party createParty(CreateUpdatePartyDTO request) {
        return null;
    }

    public List<Party> getAllParties() {
        return partyRepository.findAll();
    }

    public Party getPartyById(Long id) {
        return partyRepository.findById(id).orElse(null);
    }

    public void deletePartyById(Long id) {
        partyRepository.deleteById(id);
    }
}
