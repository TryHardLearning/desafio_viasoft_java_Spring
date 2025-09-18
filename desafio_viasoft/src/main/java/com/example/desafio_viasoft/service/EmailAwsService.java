package com.example.desafio_viasoft.service;

import com.example.desafio_viasoft.dto.EmailAwsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class EmailAwsService {

    private final ObjectMapper objectMapper;

    public EmailAwsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String processEmail(@Valid EmailAwsDTO email) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(email);
        System.out.println("Service AWS:\n"+json);

        return json;
    }
}
