package br.com.softdesign.desafio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.softdesign.desafio.dto.AssociateDTO;
import br.com.softdesign.desafio.dto.AssociateResponseDTO;
import br.com.softdesign.desafio.service.IAssociateService;

@WebMvcTest(controllers = AssociateController.class)
public class AssociateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAssociateService associateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAssociate() throws Exception {
        AssociateDTO associateDTO = new AssociateDTO();
        associateDTO.setName("Associado 1");
        associateDTO.setDocument("12345678901");

        AssociateResponseDTO responseDTO = new AssociateResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Associado 1");
        responseDTO.setDocument("12345678901");
        
        when(associateService.createAssociate(any(AssociateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/associates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(associateDTO)))
                .andExpect(status().isCreated());
    }
}
