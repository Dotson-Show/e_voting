package com.evoter.poll.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddPollRequest {

    private String pollName;

    private List<String> candidateNames;

}
