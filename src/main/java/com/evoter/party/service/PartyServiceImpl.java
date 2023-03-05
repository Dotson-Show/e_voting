package com.evoter.party.service;


import com.evoter.exception.GeneralException;
import com.evoter.general.enums.ResponseCodeAndMessage;
import com.evoter.party.dto.request.PartyRequestDto;
import com.evoter.party.dto.response.PartyResponseDto;
import com.evoter.party.model.Party;
import com.evoter.party.repository.PartyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PartyServiceImpl implements PartyService {

    private final PartyRepository partyRepository;

    public PartyServiceImpl(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }


    @Override
    public PartyResponseDto createParty(PartyRequestDto request) {
        log.info("Creating new party for {}",request);

        Party party = new Party();
        if(request ==null) throw new GeneralException(ResponseCodeAndMessage.INVALID_JSON_REQUEST_DATA_90
                .responseCode,"request cannot be null");

        party.setName(request.getName());
        partyRepository.save(party);

        PartyRequestDto partyRequestDto = new PartyRequestDto();
        partyRequestDto.setName(party.getName());

        return new PartyResponseDto();
    }

    @Override
    public PartyResponseDto updateParty(Long partyId, PartyRequestDto request) {
        log.info("Request to update an party party id = {} with payload = {}",partyId,request);

        Party partyFound = (partyRepository.findById(partyId)).orElseThrow(()-> new
                        GeneralException(ResponseCodeAndMessage.RECORD_NOT_FOUND_88.responseCode,"Party not found"));


        PartyResponseDto partyResponseDto = new PartyResponseDto();
        partyResponseDto.setName(partyFound.getName());
        return partyResponseDto;

    }

    public List<Party> getAllParties() {return partyRepository.findAll();
    }

    public Party getPartyById(Long id) {
        return partyRepository.findById(id).orElse(null);
    }

    public void deletePartyById(Long id) {
        partyRepository.deleteById(id);
    }
}
