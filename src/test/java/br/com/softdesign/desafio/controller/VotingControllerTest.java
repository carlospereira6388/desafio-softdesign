package br.com.softdesign.desafio.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.softdesign.desafio.dto.AgendaDTO;
import br.com.softdesign.desafio.dto.AgendaResponseDTO;
import br.com.softdesign.desafio.dto.ResultDTO;
import br.com.softdesign.desafio.service.IVotingService;

@WebMvcTest(controllers = VotingController.class)
public class VotingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IVotingService votingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAgenda() throws Exception {
        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setName("Pauta 1");
        agendaDTO.setDescription("Desc 1");

        AgendaResponseDTO responseDTO = new AgendaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Pauta 1");
        responseDTO.setDescription("Desc 1");

        when(votingService.createAgenda(any(AgendaDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/agendas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agendaDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pauta 1"))
                .andExpect(jsonPath("$.description").value("Desc 1"));
    }

    @Test
    void shouldGetVotingResult() throws Exception {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setAgendaId(1L);
        resultDTO.setAgendaName("Pauta 1");
        resultDTO.setTotalVotes(10);
        resultDTO.setYesVotes(7);
        resultDTO.setNoVotes(3);
        resultDTO.setResult("Aprovada");

        when(votingService.getVotingResult(anyLong())).thenReturn(resultDTO);
        
        mockMvc.perform(get("/api/v1/agendas/1/results"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agendaId").value(1L))
                .andExpect(jsonPath("$.agendaName").value("Pauta 1"))
                .andExpect(jsonPath("$.totalVotes").value(10))
                .andExpect(jsonPath("$.yesVotes").value(7))
                .andExpect(jsonPath("$.noVotes").value(3))
                .andExpect(jsonPath("$.result").value("Aprovada"));
    }
}
