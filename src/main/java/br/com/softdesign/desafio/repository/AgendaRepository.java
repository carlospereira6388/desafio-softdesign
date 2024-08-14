package br.com.softdesign.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.softdesign.desafio.model.Agenda;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}