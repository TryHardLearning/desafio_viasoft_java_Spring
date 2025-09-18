package com.example.desafio_viasoft.service;

import com.example.desafio_viasoft.dto.EmailAwsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AWS_EmailServiceTest {

    @Autowired
    private EmailAwsService service;

    @Test
    void test_AWS_ProcessEmail_ValidEmail_ReturnsJson() throws JsonProcessingException {
        EmailAwsDTO email = EmailAwsDTO.builder()
                .recipient("teste@teste.com")
                .recipientName("João da Silva")
                .sender("noreply@teste.com")
                .subject("Assunto de teste")
                .content("Conteúdo do e-mail de teste")
                .build();

        String result = service.processEmail(email);

        assertThat(result).contains("teste@teste.com");
        assertThat(result).contains("João da Silva");
        assertThat(result).contains("Assunto de teste");
    }

    /// Size_max = 45
    @Test
    void test_AWS_ProcessEmail_RecipientExceedsSize_ThrowsException() {
        EmailAwsDTO email = EmailAwsDTO.builder()
                .recipient("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com")
                .recipientName("João")
                .sender("noreply@teste.com")
                .subject("Assunto")
                .content("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }

    ///  Size_max = 120
    @Test
    void test_AWS_ProcessEmail_SubjectExceedsSize_ThrowsException() {
        EmailAwsDTO email = EmailAwsDTO.builder()
                .recipient("teste@teste.com")
                .recipientName("João")
                .sender("noreply@teste.com")
                .subject("Este assunto é propositalmente aaaaaaamuito grande para ultrapassar o limite de cem caracteres estabelecido pela anotação Size\",\n" +
                        "  \"body\": \"Este conteúdo foi escrito apenas para garantir que o limite de duzentos e cinquenaaaaaaaata caracteres seja ultrapassado. " +
                        "Vamos adicionar mais palavras, frases longas e textos repetidos para alcançar esse objetivo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Sed do eiusmod tempor incididunt ut labore et dolore magna aliquaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.")
                .content("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }

    ///  Size_max = 60
    @Test
    void test_AWS_ProcessEmail_RecipienteNameExceedsSize_ThrowsException() {
        EmailAwsDTO email = EmailAwsDTO.builder()
                .recipient("teste@teste.com")
                .recipientName("Joãoaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa00000000000000000aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .sender("noreply@teste.com")
                .subject("Algo")
                .content("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }
    ///  Size_max = 45
    @Test
    void test_AWS_ProcessEmail_SenderExceedsSize_ThrowsException() {
        EmailAwsDTO email = EmailAwsDTO.builder()
                .recipient("teste@teste.com")
                .recipientName("Joãoa")
                .sender("noreplyaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa000000aaaaa@teste.com")
                .subject("Algo")
                .content("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }
    ///  Size_max = 256
    @Test
    void test_AWS_ProcessEmail_ContentExceedsSize_ThrowsException() {
        EmailAwsDTO email = EmailAwsDTO.builder()
                .recipient("teste@teste.com")
                .recipientName("Joãoa")
                .sender("noreplyaaaaa@teste.com")
                .subject("Algo")
                .content("C".repeat(257))
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
