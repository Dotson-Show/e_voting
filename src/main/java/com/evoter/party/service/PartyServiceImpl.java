package com.evoter.party.service;

import com.evoter.Exception.PartyException;
import com.evoter.exception.GeneralException;
import com.evoter.party.dto.CreatePartyDTO;
import com.evoter.party.dto.UpdatePartyRequest;
import com.evoter.party.model.Party;
import com.evoter.party.repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PartyServiceImpl implements PartyService{

    private final PartyRepository partyRepository;

    public PartyServiceImpl(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }


    @Override
    public Party createParty(CreatePartyDTO request) {
        Party party = new Party();
        if(request != null){
            party.setName(request.getName());
            partyRepository.save(party);
        }

        return party;
    }

    @Override
    public Party updateParty(Long partyId, UpdatePartyRequest request) {

      Optional<Party> partyFound = partyRepository.findById(partyId);
      if(partyFound.isEmpty()) throw new PartyException(String.format("Party with id %d does not exists",partyId));

      Party party = partyFound.get();

      party.setName(request.getName());
      partyRepository.save(party);
        return party;
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
