package com.evoter.service;

import com.evoter.party.dto.request.PartyRequestDto;
import com.evoter.party.dto.response.PartyResponseDto;
import com.evoter.party.model.Party;
import com.evoter.party.service.PartyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PartyServiceImplTest {

    private final PartyService partyService;
    private PartyRequestDto partyRequestDto;


    @Autowired
    public PartyServiceImplTest(PartyService partyService) {
        this.partyService = partyService;
    }

   @BeforeEach
    void setUp(){
    partyRequestDto = new PartyRequestDto();
    partyRequestDto.setName("All progressive Party");


}

  @Test
    void createPartyTest(){
      PartyResponseDto party = partyService.createParty(partyRequestDto);
      assertThat(party).isNotNull();
  }

  @Test
    void updatePartyTest(){
     PartyResponseDto party = partyService.updateParty(1L,partyRequestDto);
      assertThat(party).isNotNull();
      assertThat(partyRequestDto.getName()).isNotNull();
  }
}