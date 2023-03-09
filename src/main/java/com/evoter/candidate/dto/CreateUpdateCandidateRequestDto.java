package com.evoter.candidate.dto;

import lombok.Data;

/**
 * @author ehis
 */

@Data
public class CreateUpdateCandidateRequestDto {


    private Long partyId;

    private Long pollTypeId;

    private String name;

    private String sex;

    private String email;

    private int age;


}
