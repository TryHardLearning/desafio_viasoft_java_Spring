package com.example.desafio_viasoft.service;

import com.example.desafio_viasoft.dto.EmailOciDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OCI_EmailServiceTest {

    @Autowired
    private EmailOciService service;

    @Test
    void test_OCI_ProcessEmail_ValidEmail_ReturnsJson() throws JsonProcessingException {
        EmailOciDTO email = EmailOciDTO.builder()
                .recipientEmail("teste@teste.com")
                .recipientName("João da Silva")
                .senderEmail("noreply@teste.com")
                .subject("Assunto de teste")
                .body("Conteúdo do e-mail de teste")
                .build();

        String result = service.processEmail(email);

        assertThat(result).contains("teste@teste.com");
        assertThat(result).contains("João da Silva");
        assertThat(result).contains("Assunto de teste");
    }

    /// Size_max = 40
    @Test
    void test_OCI_ProcessEmail_RecipientEmailExceedsSize_ThrowsException() {
        EmailOciDTO email = EmailOciDTO.builder()
                .recipientEmail("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com")
                .recipientName("João")
                .senderEmail("noreply@teste.com")
                .subject("Assunto")
                .body("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }

    ///  Size_max = 100
    @Test
    void test_OCI_ProcessEmail_SubjectExceedsSize_ThrowsException() {
        EmailOciDTO email = EmailOciDTO.builder()
                .recipientEmail("teste@teste.com")
                .recipientName("João")
                .senderEmail("noreply@teste.com")
                .subject("Este assunto é propositalmente aaaaaaamuito grande para ultrapassar o limite de cem caracteres estabelecido pela anotação Size\",\n" +
                        "  \"body\": \"Este conteúdo foi escrito apenas para garantir que o limite de duzentos e cinquenaaaaaaaata caracteres seja ultrapassado. " +
                        "Vamos adicionar mais palavras, frases longas e textos repetidos para alcançar esse objetivo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                        "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
                .body("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }

    ///  Size_max = 50
    @Test
    void test_OCI_ProcessEmail_RecipienteNameExceedsSize_ThrowsException() {
        EmailOciDTO email = EmailOciDTO.builder()
                .recipientEmail("teste@teste.com")
                .recipientName("Joãoaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                .senderEmail("noreply@teste.com")
                .subject("Algo")
                .body("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }
    ///  Size_max = 50
    @Test
    void test_OCI_ProcessEmail_SenderEmailExceedsSize_ThrowsException() {
        EmailOciDTO email = EmailOciDTO.builder()
                .recipientEmail("teste@teste.com")
                .recipientName("Joãoa")
                .senderEmail("noreplyaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa000000aaaaa@teste.com")
                .subject("Algo")
                .body("Conteúdo")
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }
    ///  Size_max = 250
    @Test
    void test_OCI_ProcessEmail_BodyExceedsSize_ThrowsException() {
        EmailOciDTO email = EmailOciDTO.builder()
                .recipientEmail("teste@teste.com")
                .recipientName("Joãoa")
                .senderEmail("noreplyaaaaa@teste.com")
                .subject("Algo")
                .body("C".repeat(251))
                .build();

        assertThatThrownBy(() -> service.processEmail(email))
                .isInstanceOf(ConstraintViolationException.class);
    }

}
