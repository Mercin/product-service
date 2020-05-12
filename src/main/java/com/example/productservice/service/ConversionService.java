package com.example.productservice.service;

import com.example.productservice.util.CurrencyEnum;

import java.io.IOException;

public interface ConversionService {
    public Long convertValue(CurrencyEnum startingEnum, CurrencyEnum targetEnum, int startingAmount) throws IOException;
}
