package com.example.productservice.dto;

import lombok.Data;

@Data
public class FixerQuery {
    private String from;
    private String to;
    private int amount;
}
