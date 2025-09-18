package com.example.desafio_viasoft.service;

import com.example.desafio_viasoft.dto.EmailOciDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class EmailOciService {

    private final ObjectMapper objectMapper;

    public EmailOciService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String processEmail(@Valid EmailOciDTO email) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(email);
        System.out.println("Service OCI:\n"+json);

        return json;
    }
}
