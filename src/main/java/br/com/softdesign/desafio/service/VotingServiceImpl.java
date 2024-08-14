package br.com.softdesign.desafio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.softdesign.desafio.dto.AgendaDTO;
import br.com.softdesign.desafio.dto.AgendaResponseDTO;
import br.com.softdesign.desafio.dto.ResultDTO;
import br.com.softdesign.desafio.dto.VoteDTO;
import br.com.softdesign.desafio.exception.CustomException;
import br.com.softdesign.desafio.model.Agenda;
import br.com.softdesign.desafio.model.Associate;
import br.com.softdesign.desafio.model.Vote;
import br.com.softdesign.desafio.repository.AgendaRepository;
import br.com.softdesign.desafio.repository.AssociateRepository;
import br.com.softdesign.desafio.repository.VoteRepository;
import br.com.softdesign.desafio.utils.Mapper;

@Service
public class VotingServiceImpl implements IVotingService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private VoteRepository voteRepository;
        
    @Autowired
    private ICpfValidationService cpfValidationService;
    
    @Autowired
    private AssociateRepository associateRepository;
    
    @Autowired
    private Mapper mapper;

    @Override
    public AgendaResponseDTO createAgenda(AgendaDTO agendaDTO) {
        Agenda agenda = mapper.convert(agendaDTO, Agenda.class);
        agenda = agendaRepository.save(agenda);
        return mapper.convert(agenda, AgendaResponseDTO.class);
    }
    
    @CacheEvict(value = "votingResults", allEntries = true)
    @Override
    public void openVotingSession(Long id, Long duration) {
        Agenda agenda = agendaRepository.findById(id)
                .orElseThrow(() -> new CustomException("Pauta não encontrada", HttpStatus.NOT_FOUND));
        agenda.setSessionOpen(true);
        agenda.setSessionEndTime(System.currentTimeMillis() + (duration != null ? duration : 60000));
        agendaRepository.save(agenda);
    }
    
    @Override
    public VoteDTO vote(Long agendaId, VoteDTO voteDTO) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new CustomException("Pauta não encontrada", HttpStatus.NOT_FOUND));
        
        Associate associate = associateRepository.findById(voteDTO.getAssociateId())
                .orElseThrow(() -> new CustomException("Associado não encontrado", HttpStatus.NOT_FOUND));
        
        if (System.currentTimeMillis() > agenda.getSessionEndTime()) {
            agenda.setSessionOpen(false);
            agendaRepository.save(agenda);
            throw new CustomException("Sessão de votação encerrada", HttpStatus.BAD_REQUEST);
        }
        
        if (!cpfValidationService.canVote(associate.getDocument())) {
            throw new CustomException("Usuário não está autorizado", HttpStatus.FORBIDDEN);
        }
        
        if (voteRepository.existsByAgendaIdAndAssociate(agendaId, associate)) {
            throw new CustomException("Associado já votou nesta pauta", HttpStatus.BAD_REQUEST);
        }
        
        boolean voteValue = "Sim".equalsIgnoreCase(voteDTO.getVote());
        Vote vote = new Vote();
        vote.setAgendaId(agendaId);
        vote.setAssociate(associate);
        vote.setVote(voteValue);
        Vote savedVote = voteRepository.save(vote);
        
        return mapper.convert(savedVote, VoteDTO.class);
    }

    @Cacheable(value = "votingResults", key = "#id")
    @Override
    public ResultDTO getVotingResult(Long agendaId) {
        Agenda agenda = agendaRepository.findById(agendaId)
                .orElseThrow(() -> new CustomException("Pauta não encontrada", HttpStatus.NOT_FOUND));

        List<Vote> votes = voteRepository.findByAgendaId(agendaId);

        int yesVotes = (int) votes.stream().filter(Vote::isVote).count();
        int noVotes = votes.size() - yesVotes;

        ResultDTO result = new ResultDTO();
        result.setAgendaId(agenda.getId());
        result.setAgendaName(agenda.getName());
        result.setTotalVotes(votes.size());
        result.setYesVotes(yesVotes);
        result.setNoVotes(noVotes);
        result.setResult(yesVotes > noVotes ? "Aprovada" : "Rejeitada");
        
        return result;
    }
}