package br.com.softdesign.desafio.service;

import br.com.softdesign.desafio.dto.AgendaDTO;
import br.com.softdesign.desafio.dto.AgendaResponseDTO;
import br.com.softdesign.desafio.dto.ResultDTO;
import br.com.softdesign.desafio.dto.VoteDTO;

public interface IVotingService {
	
    AgendaResponseDTO createAgenda(AgendaDTO agendaDTO);
    void openVotingSession(Long id, Long duration);
    VoteDTO vote(Long agendaId, VoteDTO voteDTO);
    ResultDTO getVotingResult(Long agendaId);
}