package br.com.softdesign.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softdesign.desafio.model.Associate;
import br.com.softdesign.desafio.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    
	List<Vote> findByAgendaId(Long agendaId);
	
	boolean existsByAgendaIdAndAssociate(Long agendaId, Associate associateId);
}