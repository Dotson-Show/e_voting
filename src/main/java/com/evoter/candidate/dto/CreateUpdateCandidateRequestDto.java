package com.evoter.candidate.dto;

import lombok.Data;

/**
 * @author ehis
 */

@Data
public class CreateUpdateCandidateRequestDto {


    private Integer partyId;

    private Integer pollTypeId;

    private String name;

    private String sex;

    private Integer age;


}
