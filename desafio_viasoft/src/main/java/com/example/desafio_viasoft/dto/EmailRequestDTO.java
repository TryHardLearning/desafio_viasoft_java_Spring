package com.example.desafio_viasoft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailRequestDTO {

    @NotBlank
    private String to;

    @NotBlank
    private String recipientName;

    @NotBlank
    private String from;

    @NotBlank
    private String subject;

    @NotBlank
    private String body;
}
