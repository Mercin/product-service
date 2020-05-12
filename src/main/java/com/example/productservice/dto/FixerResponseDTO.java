package com.example.productservice.dto;

import lombok.Data;

@Data
public class FixerResponseDTO {
    private boolean success;
    private FixerQuery query;
    private FixerInfo info;
    private String historical;
    private String date;
    private long result;
}
