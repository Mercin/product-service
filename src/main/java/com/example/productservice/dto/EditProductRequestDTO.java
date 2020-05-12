package com.example.productservice.dto;

import com.example.productservice.util.CurrencyEnum;
import lombok.Data;

import java.util.UUID;

@Data
public class EditProductRequestDTO {
    private Integer priceInCents;
    private CurrencyEnum currency;
    private UUID categoryId;
    private String productName;
}
