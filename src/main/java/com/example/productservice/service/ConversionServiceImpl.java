package com.example.productservice.service;

import com.example.productservice.config.FixerConfig;
import com.example.productservice.dto.FixerResponseDTO;
import com.example.productservice.util.CurrencyEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversionServiceImpl implements ConversionService{

    private final FixerConfig fixerConfig;

    private final ObjectMapper objectMapper;

    @Override
    public Long convertValue(CurrencyEnum startingEnum, CurrencyEnum targetEnum, int startingAmount) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet((fixerConfig + "?access_key=" + fixerConfig.getApiKey()
                    + "&from=" + startingEnum.name()
                    + "&to=" + targetEnum.name()
                    + "&amount=" + startingAmount));

            System.out.println("Executing request " + httpget.getRequestLine());

            final CloseableHttpResponse response = httpclient.execute(httpget);

            FixerResponseDTO fixerResponse = objectMapper.readValue(response.getEntity().getContent(), FixerResponseDTO.class);

            if(fixerResponse != null) {
                return fixerResponse.getResult();
            }

        } catch (IOException e) {
            log.error("Failed to convert", e);
        } finally {
            httpclient.close();
        }
        return null;
    }
}
