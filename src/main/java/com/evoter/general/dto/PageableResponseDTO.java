package com.evoter.general.dto;

import lombok.Data;

@Data
public class PageableResponseDTO {
    private boolean hasNextRecord;
    private int totalCount;
}
