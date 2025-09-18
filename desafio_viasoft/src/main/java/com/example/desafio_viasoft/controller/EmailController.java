package com.example.desafio_viasoft.controller;

import com.example.desafio_viasoft.dto.EmailAwsDTO;
import com.example.desafio_viasoft.dto.EmailOciDTO;
import com.example.desafio_viasoft.dto.EmailRequestDTO;
import com.example.desafio_viasoft.service.EmailAwsService;
import com.example.desafio_viasoft.service.EmailOciService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Value("${mail.integracao}")
    private String provider;

    private final ModelMapper mapper;
    private final EmailAwsService emailAwsService;
    private final EmailOciService emailOciService;

    public EmailController(ModelMapper mapper, EmailAwsService emailAwsService, EmailOciService emailOciService) {
        this.mapper = mapper;
        this.emailAwsService = emailAwsService;
        this.emailOciService = emailOciService;
    }
    @PostMapping
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequestDTO request) {
        String responseJSONEmail = null;

        if(provider == null || provider.isEmpty()) {
            return ResponseEntity.internalServerError().body("Falha em integração com serviço de email");
        }

        try{
            switch (provider.toUpperCase()) {
                case "AWS":
                    EmailAwsDTO emailAws = mapper.map(request, EmailAwsDTO.class);
                    responseJSONEmail = emailAwsService.processEmail(emailAws);
                    break;

                case "OCI":
                    EmailOciDTO emailOci = mapper.map(request, EmailOciDTO.class);
                    responseJSONEmail = emailOciService.processEmail(emailOci);
                    break;

                default:
                    return ResponseEntity.badRequest().body("Sem serviço de email");
            }

        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro. Tamanho dos campos da requsição extrapola o limite predefinido.");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

        System.out.println("Controller:\n"+responseJSONEmail);

        return ResponseEntity.noContent().build();
    }
}
