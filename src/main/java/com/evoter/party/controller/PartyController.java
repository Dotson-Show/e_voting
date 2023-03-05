package com.evoter.party.controller;

import com.evoter.party.dto.CreatePartyDTO;
import com.evoter.party.dto.UpdatePartyRequest;
import com.evoter.party.model.Party;
import com.evoter.party.service.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author showunmioludotun
 */
@RestController
@RequestMapping("/api/v1/parties")
public class PartyController {
    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Party> addParty(@RequestBody CreatePartyDTO request) {
        try {
            Party savedParty = partyService.createParty(request);
            if (savedParty == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(savedParty, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("{partyId}")
    public ResponseEntity<?>updateParty(@PathVariable Long partyId, @RequestBody UpdatePartyRequest updatePartyRequest){
       Party updatedParty = partyService.updateParty(partyId,updatePartyRequest);
       return ResponseEntity.status(HttpStatus.OK).body(updatedParty);
    }

    @GetMapping("/parties")
    public ResponseEntity<List<Party>> getAllParties() {
        try {
            List<Party> parties = partyService.getAllParties();
            if (parties.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(parties, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/parties/{partyId}")
    public ResponseEntity<Party> getPartyById(@PathVariable("partyId") Long id) {
        try {
            Party party = partyService.getPartyById(id);
            if (party == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(party, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/parties/{partyId}")
    public ResponseEntity<Void> deletePartyById(@PathVariable("partyId") Long id) {
        try {
            partyService.deletePartyById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
