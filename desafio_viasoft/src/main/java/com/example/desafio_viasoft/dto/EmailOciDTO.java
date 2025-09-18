package com.example.desafio_viasoft.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailOciDTO {

    @Size(max = 40)
    private String recipientEmail;

    @Size(max = 50)
    private String recipientName;

    @Size(max = 40)
    private String senderEmail;

    @Size(max = 100)
    private String subject;

    @Size(max = 250)
    private String body;

}
