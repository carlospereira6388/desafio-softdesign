package br.com.softdesign.desafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.softdesign.desafio.dto.AssociateDTO;
import br.com.softdesign.desafio.dto.AssociateResponseDTO;
import br.com.softdesign.desafio.exception.CustomException;
import br.com.softdesign.desafio.model.Associate;
import br.com.softdesign.desafio.repository.AssociateRepository;
import br.com.softdesign.desafio.utils.Mapper;

public class AssociateServiceTest {

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private AssociateServiceImpl associateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAssociate() {
        AssociateDTO associateDTO = new AssociateDTO();
        associateDTO.setName("Associado 1");
        associateDTO.setDocument("12345678901");

        Associate associate = new Associate();
        associate.setId(1L);
        associate.setName("Associado 1");
        associate.setDocument("12345678901");

        AssociateResponseDTO responseDTO = new AssociateResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Associado 1");
        responseDTO.setDocument("12345678901");

        when(associateRepository.findByDocument(any(String.class))).thenReturn(Optional.empty());
        when(associateRepository.save(any(Associate.class))).thenReturn(associate);
        when(mapper.convert(any(AssociateDTO.class), any())).thenReturn(associate);
        when(mapper.convert(any(Associate.class), any())).thenReturn(responseDTO);

        AssociateResponseDTO result = associateService.createAssociate(associateDTO);

        assertEquals("Associado 1", result.getName());
        assertEquals("12345678901", result.getDocument());
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        AssociateDTO associateDTO = new AssociateDTO();
        associateDTO.setName("Associado 1");
        associateDTO.setDocument("12345678901");

        when(associateRepository.findByDocument(any(String.class))).thenReturn(Optional.of(new Associate()));

        assertThrows(CustomException.class, () -> {
            associateService.createAssociate(associateDTO);
        });
    }
}
