package com.evoter.service;

import com.evoter.party.dto.CreatePartyDTO;
import com.evoter.party.dto.UpdatePartyRequest;
import com.evoter.party.model.Party;
import com.evoter.party.service.PartyService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PartyServiceImplTest {

    private final PartyService partyService;
    private  CreatePartyDTO createPartyDTO;

    private UpdatePartyRequest updatePartyRequest;

    @Autowired
    public PartyServiceImplTest(PartyService partyService) {
        this.partyService = partyService;
    }

   @BeforeEach
    void setUp(){
    createPartyDTO = new CreatePartyDTO();
    createPartyDTO.setName("All progressive Party");
    updatePartyRequest.setName("PDP");

}

  @Test
    void createPartyTest(){
      Party party = partyService.createParty(createPartyDTO);
      assertThat(party).isNotNull();
  }

  @Test
    void updatePartyTest(){
     Party party = partyService.updateParty(1L,updatePartyRequest);
      System.out.println(party.toString());
      assertThat(party).isNotNull();
      assertThat(updatePartyRequest.getName()).isNotNull();
  }
}