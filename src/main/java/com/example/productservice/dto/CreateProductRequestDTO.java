package com.example.productservice.dto;

import com.example.productservice.util.CurrencyEnum;
import lombok.Data;

import java.util.UUID;

import javax.validation.constraints.NotNull;

@Data
public class CreateProductRequestDTO {
    @NotNull
    private String productName;

    @NotNull
    private CurrencyEnum currency;

    private int priceInCents;

    @NotNull
    private UUID categoryId;
}
