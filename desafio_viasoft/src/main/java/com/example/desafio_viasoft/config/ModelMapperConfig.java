package com.example.desafio_viasoft.config;

import com.example.desafio_viasoft.dto.EmailAwsDTO;
import com.example.desafio_viasoft.dto.EmailOciDTO;
import com.example.desafio_viasoft.dto.EmailRequestDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Conversão para AWS
        mapper.addMappings(new PropertyMap<EmailRequestDTO, EmailAwsDTO>() {
            @Override
            protected void configure() {
                map().setRecipient(source.getTo());
                map().setRecipientName(source.getRecipientName());
                map().setSender(source.getFrom());
                map().setSubject(source.getSubject());
                map().setContent(source.getBody());
            }
        });

        // Conversão para OCI
        mapper.addMappings(new PropertyMap<EmailRequestDTO, EmailOciDTO>() {
            @Override
            protected void configure() {
                map().setRecipientEmail(source.getTo());
                map().setRecipientName(source.getRecipientName());
                map().setSenderEmail(source.getFrom());
                map().setSubject(source.getSubject());
                map().setBody(source.getBody());
            }
        });

        return mapper;
    }
}
