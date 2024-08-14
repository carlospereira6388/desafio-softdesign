package br.com.softdesign.desafio.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import br.com.softdesign.desafio.dto.AgendaDTO;
import br.com.softdesign.desafio.dto.AgendaResponseDTO;
import br.com.softdesign.desafio.exception.CustomException;
import br.com.softdesign.desafio.model.Agenda;
import br.com.softdesign.desafio.repository.AgendaRepository;
import br.com.softdesign.desafio.utils.Mapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class VotingServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private VotingServiceImpl votingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAgenda() {
        AgendaDTO agendaDTO = new AgendaDTO();
        agendaDTO.setName("Pauta 1");
        agendaDTO.setDescription("Desc 1");

        Agenda agenda = new Agenda();
        agenda.setId(1L);
        agenda.setName("Agenda 1");
        agenda.setDescription("Desc 1");

        AgendaResponseDTO responseDTO = new AgendaResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Agenda 1");
        responseDTO.setDescription("Desc 1");

        when(agendaRepository.save(any(Agenda.class))).thenReturn(agenda);
        when(mapper.convert(any(AgendaDTO.class), any())).thenReturn(agenda);
        when(mapper.convert(any(Agenda.class), any())).thenReturn(responseDTO);

        AgendaResponseDTO result = votingService.createAgenda(agendaDTO);

        assert(result.getId() != null);
    }

    @Test
    void shouldThrowExceptionWhenAgendaNotFound() {
        when(agendaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomException.class, () -> {
            votingService.openVotingSession(1L, 60000L);
        });
    }
}
